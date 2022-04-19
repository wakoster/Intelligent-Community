<template>
  <div class="chat">
    <div class="talkList"><!-- 聊天列表 -->
      <div class="search"><!-- 搜索栏 -->
        <div class="search-iconfont"><span class="iconfont" onclick="document.querySelector('.search').classList.toggle('active')">&#xe604;</span></div>
        <div class="search-input">
          <input type="text" placeholder="Search" id="search-input">
        </div>
        <div class="clear-iconfont"><span class="iconfont" onclick="document.getElementById('search-input').value = ''">&#xe617;</span></div>
      </div>
      <div class="talkTag"><!-- 聊天简略信息 -->
        <div v-for="item in talkTag.data" :key="item.id">
          <div class="talkbox" :class="{active: item.userId == info.userId}" @click="item.unreadMessage = 0;clickTalkBox($event,item)">
            <div class="talkcircle">
              <img class="img" :src='item.userImg'>
              <!-- <img class="img" src='../../assets/logo.png'> -->
            </div>
            <div class="unreadNumber" v-if="item.unreadMessage > 0">{{item.unreadMessage > 99 ? '···' : item.unreadMessage}}</div>
            <label class="userName">{{item.userName}}</label>
            <label class="talkInfo">{{item.talkInfo}}</label>
            <label class="talkTime">{{formatDate(item.talkTime)}}</label>
          </div>
        </div>
      </div>
    </div>
    <div class="session">
      <div class="session-box" v-if="info.userId !== undefined">
        <label class="userName">{{info.userName}}</label>
        <div class="session-window">
          <div class="more hidden" @click="moreChattingRecord(true)">查看更多消息</div>
          <div v-for="item in sessionMsg.data" :key="item.userId">
            <div class="session-msg"  :class="{receiver: item.senderUserInfo !== info.userId}">
              <img class="userImg" :src='info.userImg' v-if="item.senderUserInfo === info.userId">
              <img class="userImg" :src='selfUserInfo.userImg' v-if="item.senderUserInfo !== info.userId">
              <div class="msg-detail">
                <div class="msg-title">
                  <label class="user-name" v-if="item.senderUserInfo === info.userId">{{info.userName}}</label>
                  <label class="user-name" v-if="item.senderUserInfo !== info.userId">我</label>
                  <label class="msg-time">{{formatDate(item.createTime)}}</label>
                </div>
                <div class="msg-conttent">{{item.chattingRecord}}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="chatting" v-if="info.userId !== undefined">
          <textarea class="info" name="talk" id="talk" placeholder="请输入内容" v-model="chattingRecord.chattingRecord"></textarea>
          <div class="send">
            <button class="button" type="submit" @click="webSocketSend('111')">发送</button>
          </div>
        </div>
        <div class="userInfo" v-if="info.userId !== undefined" tabindex="0" onblur="document.querySelector('.userInfo .pop.active') !== null?document.querySelector('.userInfo .pop.active').classList.toggle('active'):'无事发生'">
          <span class="iconfont" onclick="document.querySelector('.userInfo .pop').classList.toggle('active')" @click="getUserInfo">&#xe6d3;</span>
          <div class="pop">
            <img class="userImg" :src='userInformation.data.userImg'>
            <div class="userName">{{userInformation.data.name}}</div>
            <div class="userId">编 号: {{userInformation.data.id}}</div>
            <div class="line"></div>
            <div class="userPhone">电话号码:&emsp;{{userInformation.data.phoneNumber}}</div>
            <div class="userPhone">电子邮件:&emsp;{{userInformation.data.mailbox}}</div>
            <div class="userAddres">地&emsp;&emsp;区:&emsp;{{userInformation.data.address}}</div>
            <div class="userDepartment">部&emsp;&emsp;门:&emsp;{{userInformation.data.department}}</div>
          </div>
        </div>
      </div>
    </div>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import {getDate} from '../../common/date.js'
import errorMessage from '../message.vue'
export default {
  components: { errorMessage },
  name: 'Chat',
  data: function () {
    return {
      chattingRecord: {
        createTime: null,
        senderUserInfo: null,
        receiverUserInfo: null,
        chattingRecord: null
      },
      socket: '',
      selfUserInfo: {
        id: 1
      },
      info: {
        userId: null,
        userName: null,
        userImg: null,
        chattingRecordPage: 0
      },
      talkTag: {},
      sessionMsg: {},
      userInformation: {
        code: null,
        msg: null,
        total: null,
        data: {
          userImg: null,
          name: null,
          id: null,
          phoneNumber: null,
          mailbox: null,
          address: null,
          department: null
        }
      }
    }
  },
  mounted: function () {
    this.info.userId = this.$route.query.userId
    this.getSelfUserInfo()
    this.getChatList()
  },
  methods: {
    getSelfUserInfo () {
      // 查询自身信息
      this.$axios
        .get('/user/getSelfUserInfo')
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.selfUserInfo = data.data
            this.webSocketInit()
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    clickTalkBox (event, item) {
      this.sessionMsg = {}
      this.info.userId = item.userId
      this.info.userName = item.userName
      this.info.userImg = item.userImg
      this.info.chattingRecordPage = 0
      if (document.querySelector('.more.hidden') !== null) {
        document.querySelector('.more').classList.add('hidden')
      }
      // 给聊天简略信息挂载active
      document.querySelectorAll('.talkbox.active').forEach((item) =>
        item.classList.remove('active'))
      event.currentTarget.className += ' active'
      this.moreChattingRecord(false)
    },
    moreChattingRecord (isMore) {
      var oldScrollHeight = null
      var oldScrollTop = null
      if (isMore) {
        // 记录滚动条位置
        oldScrollHeight = document.querySelector('.session-window').scrollHeight
        oldScrollTop = document.querySelector('.session-window').scrollTop
      }
      // 查询更多聊天信息
      this.$axios
        .post('/chat/getChattingRecord', {
          id: this.selfUserInfo.id,
          chatUserId: this.info.userId,
          page: this.info.chattingRecordPage
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            if (JSON.stringify(this.sessionMsg) === '{}') {
              this.sessionMsg = data
            } else {
              this.sessionMsg.data = data.data.concat(this.sessionMsg.data)
            }
            this.info.chattingRecordPage += 1
            if (data.data.length < 20 && document.querySelector('.more') !== null) {
              document.querySelector('.more').classList.add('hidden')
            } else if (data.data.length === 20 && document.querySelector('.more.hidden') !== null) {
              document.querySelector('.more.hidden').classList.remove('hidden')
            }
            if (!isMore) {
              this.$nextTick(() => {
                document.querySelector('.session-window').scrollTop = document.querySelector('.session-window').scrollHeight
              })
            }
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
        .finally(() => {
          if (isMore) {
            // 设置滚动条位置
            document.querySelector('.session-window').scrollTop = document.querySelector('.session-window').scrollHeight - oldScrollHeight + oldScrollTop
          }
        })
    },
    chattingScrollToBottom () {
      // 移动聊天窗口滚动条至最下
      this.$nextTick(() => {
        var container = this.$el.querySelector('.session-window')
        container.scrollTop = container.scrollHeight
      })
    },
    getChatList () {
      // 查询聊天列表
      this.$axios
        .post('/chat/getChatList', {
          phoneNumber: this.$cookies.get('cookie_userPhoneNumber'),
          userId: this.info.userId
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.talkTag = data
            if (this.info.userId !== null) {
              for (var i = 0; i < this.talkTag.data.length; i++) {
                if (this.talkTag.data[i].userId.toString() === this.info.userId) {
                  this.sessionMsg = {}
                  this.info.userId = this.talkTag.data[i].userId
                  this.info.userName = this.talkTag.data[i].userName
                  this.info.userImg = this.talkTag.data[i].userImg
                  this.info.chattingRecordPage = 0
                  this.talkTag.data[i].unreadMessage = 0
                  this.moreChattingRecord(false)
                  break
                }
              }
            }
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    getUserInfo () {
      // 查询联系人详细信息
      this.$axios
        .post('/user/getUserInfo', {
          id: this.info.userId
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.userInformation = data
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    formatDate (time) {
      if (time === null) {
        return ''
      }
      return getDate(time.toString(), 'yyyy-MM-dd hh:mm')
    },
    showErrorMessage (errorMessage) {
      this.$refs.errorMessage.setErrorMessage(errorMessage)
    },
    showInfoMessage (infoMessage) {
      this.$refs.errorMessage.setInfoMessage(infoMessage)
    },
    webSocketInit () {
      if (typeof (WebSocket) === 'undefined') {
        alert('您的浏览器不支持socket')
      } else {
        var path = 'ws://localhost:1004/websocket/' + this.selfUserInfo.id// 请求路径
        // 实例化socket
        this.socket = new WebSocket(path)
        // 监听socket连接
        this.socket.onopen = this.webSocketOpen
        // 监听socket错误信息
        this.socket.onerror = this.webSocketError
        // 监听socket消息
        this.socket.onmessage = this.webSocketGetMessage
        // 监听socket关闭
        this.socket.onclose = this.webSocketClose
      }
    },
    webSocketOpen () {
      this.showInfoMessage('通讯连接成功')
      console.log('通讯连接成功')
    },
    webSocketError () {
      this.showErrorMessage('通讯连接异常')
    },
    webSocketGetMessage (msg) {
      var chattingRecord = JSON.parse(msg.data)
      if (chattingRecord.senderUserInfo === this.info.userId || chattingRecord.receiverUserInfo === this.info.userId) {
        this.sessionMsg.data.push(chattingRecord)
        this.$nextTick(() => {
          document.querySelector('.session-window').scrollTop = document.querySelector('.session-window').scrollHeight
        })
        // 设置已读
        this.$axios
          .post('/chat/setReadMessage', {
            id: this.selfUserInfo.id,
            chatUserId: this.info.userId
          })
          .catch(err => {
            this.showErrorMessage(err)
          })
      } else {
        this.getChatList()
      }
    },
    webSocketSend () {
      if (this.chattingRecord.chattingRecord !== undefined && this.chattingRecord.chattingRecord !== null && this.chattingRecord.chattingRecord !== '') {
        this.chattingRecord.createTime = parseInt(new Date().getTime() / 1000)
        this.chattingRecord.senderUserInfo = this.selfUserInfo.id
        this.chattingRecord.receiverUserInfo = this.info.userId
        this.socket.send(JSON.stringify(this.chattingRecord))
        this.chattingRecord.chattingRecord = null
      }
    },
    webSocketClose () {
      this.showErrorMessage('通讯连接异常关闭')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="scoped">
@font-face {
  font-family: 'iconfont';  /* Project id 3046084 */
  src: url('//at.alicdn.com/t/font_3046084_xasmgha8yt.woff2?t=1640335813674') format('woff2'),
       url('//at.alicdn.com/t/font_3046084_xasmgha8yt.woff?t=1640335813674') format('woff'),
       url('//at.alicdn.com/t/font_3046084_xasmgha8yt.ttf?t=1640335813674') format('truetype');
}

.iconfont{
  font-family: "iconfont" !important;
  font-size: 26px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
  color: rgb(117, 117, 117);
}

.chat{
  width: calc(100% - 65px);
  height: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  left: 64px;
}

.chat .talkList {
  width: 300px;
  height: calc(100% - 20px);
  background-color: #FFFFFF;
  border-radius: 4px;
  position: relative;
  margin: 10px 0px 10px 15px;
}

.talkList .search{
  position: relative;
  display: flex;
  width: 40px;
  height: 40px;
  background: rgb(226,226,226);
  border-radius: 40px;
  margin: 10px auto 0;
  transition: width 0.5s;
  overflow: hidden;
}

.talkList .search.active{
  width: 275px;
}

.search-iconfont .iconfont{
  position: relative;
  top: 9px;
  left: 7px;
  cursor:pointer;
}

.clear-iconfont .iconfont{
  position: relative;
  top: 10px;
  left: 30px;
  font-size: 12px;
  cursor:pointer;
}

.search .search-input{
  position: relative;
  width: 190px;
  height: 40px;
  left: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.search .search-input input{
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100%;
  border:none;
  outline: none;
  background: rgb(226,226,226);
  color: rgb(117,117,117);
  font-size: 16px;
}

.talkList .talkTag{
  position: relative;
  margin-top: 10px;
  height: calc(100% - 60px);
  overflow: auto;
}

.talkList .talkTag::-webkit-scrollbar {
  width: 5px;
}

.talkList .talkTag::-webkit-scrollbar-thumb {
  background: #d8d8d8;
  border-radius: 10px;
}

.talkList .talkTag::-webkit-scrollbar-track-piece {
  background: transparent;
}

.talkTag .talkbox {
  border-bottom: solid;
  border-color: rgb(228, 228, 228);
  border-width: 1px;
  width: 100%;
  height: 64px;
  position: relative;
  box-sizing: content-box;
  display: flex;
}

.talkTag .talkbox:hover{
  background-color: rgb(231, 231, 231);
}

.talkTag .talkbox.active{
  background-color: rgb(201, 201, 201);
}

.talkbox .talkcircle {
  /* border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%; */
  width: 40px;
  height: 40px;
  background-color: rgb(228, 228, 228);
  position: relative;
  top: 12px;
  left: 15px;
  flex-shrink: 0;/* 防止宽度压缩 */
}

.talkcircle .img{
  font-size: 15px;
  position: relative;
  text-align:center;
  width: 100%;
  height: 100%;
  /* border-radius: 50%; */
  color: #FFFFFF;
}

.talkbox .unreadNumber{
  position: absolute;
  width: 20px;
  height: 20px;
  background: red;
  color: #fff;
  border-radius: 50%;
  font-size: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  left: 45px;
  top: 5px;
}

.talkbox .userName {
  position: relative;
  top: 12px;
  left: 30px;
  font-size: 14px;
  width: 100px;
  height: 19px;
  flex-shrink: 0;/* 防止宽度压缩 */
  white-space:nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
}

.talkbox .talkInfo {
  position: relative;
  top: 37px;
  left: -70px;
  font-size: 12px;
  width: 200px;
  height: 16px;
  color: rgb(153, 153, 153);
  flex-shrink: 0;/* 防止宽度压缩 */
  white-space:nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
}

.talkbox .talkTime {
  position: relative;
  top: 12px;
  right: 150px;
  font-size: 12px;
  width: 100px;
  height: 16px;
  color: rgb(204, 204, 204);
  flex-shrink: 0;/* 防止宽度压缩 */
  text-align: right;
  white-space: nowrap;
}

.session {
  width: calc(100% - 330px);
  height: calc(100% - 20px);
  background-color: #FFFFFF;
  border-radius: 4px;
  position: relative;
  margin: 10px 15px;
}

.session .session-box{
  width: 100%;
  height: 100%;
}

.session .userName{
  font-size: 20px;
  font-weight: 900;
  color: #333333;
  line-height: normal;
  position: relative;
  top: 15px;
  left: 20px;
}

.session .session-window{
  box-sizing: border-box;
  background-color: rgb(246,246,246);
  height: calc(100% - 200px);
  padding: 50px 20px 10px 20px;
  font-size: 12px;
  position: relative;
  top: 25px;
  overflow: auto;/* 处理溢出 */
}

.session .session-window::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}

.session .session-window::-webkit-scrollbar-thumb {
  background: #d8d8d8;
  border-radius: 10px;
}

.session .session-window::-webkit-scrollbar-track-piece {
  background: transparent;
}

.session-window .more{
  color: #006eff;
  position: absolute;
  top: 20px;
  left: calc(50% - 20px);
  cursor:pointer;
}

.session-window .more.hidden{
  display: none;
}

.session-window .session-msg{
  display: flex;
}

.session-msg .userImg{
  width: 40px;
  height: 40px;
  border-radius: 50%;
  line-height: 40px;
  text-align: center;
  color: #FFFFFF;
  margin-right: 10px;
  background-color: rgb(228, 228, 228);
}

.session-msg .msg-title{
  color:#ccc;
  margin-bottom: 6px;
}

.session-msg .msg-conttent{
  padding: 10px 12px;
  background-color: #FFFFFF;
  box-shadow: rgb(204,204,204) 0px 0px 5px 0px;
  border-radius: 4px;
  margin-bottom: 20px;
  font-size: 14px;
  display: inline-block;
}

.session-msg.receiver{
  flex-direction: row-reverse;
  text-align: right;
}

.session-msg.receiver .userImg{
  margin-left: 10px;
  margin-right: 0px;
}

.chatting{
  width: calc(100% - 40px);
  position: relative;
  margin: 20px 30px;
  top: 20px;
}

.chatting .info{
  font-size: 14px;
  font-family: PingFangSC-Regular, "PingFang SC";
  resize:none;
  outline:none;
  border-radius: 4px;
  width: 100%;
  height: 80px;
  box-sizing: border-box;
  position: relative;
  background-color: #FFFFFF;
  padding: 1px 0px 1px 0px;
  border: 2px inset rgba(0, 0, 0, 0);
  border-radius: 0px;
}

.chatting .info::-webkit-scrollbar {
  width: 5px;
}

.chatting .info::-webkit-scrollbar-thumb {
  background: #d8d8d8;
  border-radius: 10px;
}

.chatting .info::-webkit-scrollbar-track-piece {
  background: transparent;
}

.chatting .send {
  position: absolute;
  right: 20px;
}

.send .button {
  width: 80px;
  height: 30px;
  background: inherit;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
}

.send .button:hover {
  background-color: rgb(0, 110, 255);
  color: #fff;
}

.session .userInfo {
  position: absolute;
  top: 15px;
  right: 35px;
}

.userInfo .iconfont {
  font-size: 18px;
  font-weight: 400;
  line-height: normal;
  text-decoration: none;
  cursor: pointer;
}

.userInfo .pop {
  display: none;
  min-width: 200px;
  background-color: #FFFFFF;
  border-radius: 4px;
  z-index: 2;
  filter: opacity(1.0);
  position: fixed;
  right: 75px;
  box-shadow: 0px 0px 3px #888888;
  padding: 20px 30px 20px 30px;
}

.userInfo .pop.active {
  display: block;
}

.userInfo .userImg{
  height: 50px;
  width: 50px;
}

.userInfo .userName{
  position: relative;
  font-size: 24px;
  top: 0px;
  left: 0px;
}

.userInfo .userId{
  font-size: 14px;
  color:#ccc;
}

.userInfo .line{
  border-bottom: solid 1px rgb(226,226,226);
  margin: 10px 10px 10px 0px;
}
</style>
