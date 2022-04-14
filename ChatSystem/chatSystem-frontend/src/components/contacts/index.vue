<template>
  <div class="contacts">
    <div class="contactsList"><!-- 联系人列表 -->
      <div class="search"><!-- 联系人搜索栏 -->
        <div class="search-iconfont"><span class="iconfont" onclick="document.querySelector('.search').classList.toggle('active')">&#xe604;</span></div>
        <div class="search-input">
          <input type="text" placeholder="Search" id="search-input" @input="selectUserList($event.currentTarget.value)">
        </div>
        <div class="clear-iconfont"><span class="iconfont" onclick="document.getElementById('search-input').value = ''">&#xe617;</span></div>
      </div>
      <div class="contactTag"><!-- 联系人tag -->
        <div v-for="contact in contactTag.data" :key="contact.groupName">
          <div class="groupName">{{contact.groupName}}</div>
          <div v-for="item in contact.list" :key="item.userId">
            <div class="contactBox" @click="activeLink($event,item)">
              <div class="contactCircle">
                <img class="img" :src='item.userImg'>
              </div>
              <label class="userName">{{item.name}}</label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="information">
      <div class="information-box" v-if="JSON.stringify(userInfo) !=='{}'">
        <div class="user-information">
          <div class="nameAndImg-box">
            <div>
              <div class="userName">{{userInfo.name}}</div>
              <div class="userSign">{{userInfo.sign}}</div>
            </div>
            <img class="userImg" :src='userInfo.userImg'>
          </div>
          <div class="line"></div>
          <div class="otherInfo-box">
            <div class="userPhone"><label>电话号码:</label>{{userInfo.phoneNumber}}</div>
            <div class="userAddres"><label>地&emsp;&emsp;区:</label>{{userInfo.address}}</div>
            <div class="userAddres"><label>部&emsp;&emsp;门:</label>{{userInfo.department}}</div>
          </div>
          <button class="button" type="submit" @click="sendMessage(userInfo.id)">发消息</button>
        </div>
      </div>
    </div>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import errorMessage from '../message.vue'
export default {
  components: { errorMessage },
  name: 'Contacts',
  data: function () {
    return {
      contactTag: {
        code: 0,
        msg: '成功!',
        total: 3,
        data: [
          {
            groupName: '默认分组',
            list: [
              {
                id: '1',
                name: '北京客户您好，我想咨询一下资费问题',
                userImg: ''
              },
              {
                id: '2',
                name: '上海客户',
                userImg: ''
              }
            ]
          },
          {
            groupName: '朋友',
            list: [
              {
                id: '1',
                name: 'xxx',
                userImg: ''
              }
            ]
          }
        ]
      },
      userInfo: {}
    }
  },
  mounted: function () {
    this.selectUserList()
  },
  methods: {
    activeLink (event, item) {
      // 给联系人tag挂载active
      document.querySelectorAll('.contactBox.active').forEach((item) =>
        item.classList.remove('active'))
      event.currentTarget.className += ' active'
      this.userInfo = item
    },
    sendMessage (userId) {
      // 在后端解决跳转和带参问题
    },
    selectUserList (name) {
      // 查询联系人列表
      this.$axios
        .post('/user/selectUserList', {
          phoneNumber: this.$cookies.get('cookie_userPhoneNumber'),
          name: name
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.contactTag = data
            this.userInfo = {}
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    showErrorMessage (errorMessage) {
      this.$refs.errorMessage.setErrorMessage(errorMessage)
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

.contacts{
  width: calc(100% - 65px);
  height: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  left: 64px;
}

.contacts .contactsList {
  width: 300px;
  height: calc(100% - 20px);
  background-color: #FFFFFF;
  border-radius: 4px;
  position: relative;
  margin: 10px 0px 10px 15px;
}

.contactsList .search{
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

.contactsList .search.active{
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

.contactsList .contactTag{
  position: relative;
  margin-top: 10px;
  height: calc(100% - 60px);
  overflow: auto;
}

.contactsList .contactTag::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}

.contactsList .contactTag::-webkit-scrollbar-thumb {
  background: #d8d8d8;
  border-radius: 10px;
}

.contactsList .contactTag::-webkit-scrollbar-track-piece {
  background: transparent;
}

.contactTag .groupName{
  position: relative;
  margin: 10px 0px;
  font-size: 12px;
  width: calc(100% - 15px);
  color: rgb(167,167,167);
  left: 15px;
}

.contactTag .contactBox {
  border-bottom: solid;
  border-color: rgb(228, 228, 228);
  border-width: 1px;
  width: 100%;
  height: 64px;
  position: relative;
  box-sizing: content-box;
  display: flex;
}

.contactTag .contactBox:hover{
  background-color: rgb(231, 231, 231);
}

.contactTag .contactBox.active{
  background-color: rgb(201, 201, 201);
}

.contactBox .contactCircle {
  width: 40px;
  height: 40px;
  background-color: rgb(228, 228, 228);
  position: relative;
  top: 12px;
  left: 15px;
  flex-shrink: 0;/* 防止宽度压缩 */
}

.contactCircle .img{
  font-size: 15px;
  position: relative;
  text-align:center;
  width: 100%;
  height: 100%;
  color: #FFFFFF;
}

.contactBox .userName {
  position: relative;
  left: 30px;
  font-size: 14px;
  width: calc(100% - 80px);
  height: 19px;
  flex-shrink: 0;/* 防止宽度压缩 */
  margin: auto 0px;
  white-space:nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
}

.information {
  width: calc(100% - 330px);
  height: calc(100% - 20px);
  background-color: #FFFFFF;
  border-radius: 4px;
  position: relative;
  margin: 10px 15px;
}

.information .information-box{
  width: calc(100% - 40px);
  height: calc(100% - 60px);;
  margin: 20px;
  margin-top: 50px;
  overflow: scroll;
  white-space: nowrap;
  position: relative;
}

.information .information-box::-webkit-scrollbar {
  display: none;
}

.information .user-information{
  width: 350px;
  position: relative;
  line-height: 40px;
  left: 50%;
  transform: translate(-50%);
}

.user-information .nameAndImg-box{
  display: flex;
  white-space: pre-line;
}

.user-information .userImg{
  height: 50px;
  width: 50px;
  position: absolute;
  right: 20px;
  flex-shrink: 0;/* 防止宽度压缩 */
}

.user-information .userName{
  position: relative;
  font-size: 24px;
  top: 0px;
  left: 0px;
  flex-shrink: 0;/* 防止宽度压缩 */
}

.user-information .userSign{
  font-size: 14px;
  color:#ccc;
  width: 260px;
  line-height: normal;
  flex-shrink: 0;/* 防止宽度压缩 */
}

.user-information .line{
  border-bottom: solid 1px rgb(226,226,226);
  margin: 20px 10px 10px 0px;
}

.user-information label{
  font-size: 14px;
  color:#ccc;
  margin-right: 40px;
}

.user-information .otherInfo-box{
  width: 100%;
}

.user-information .button {
  width: 80px;
  height: 30px;
  background: inherit;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
  position: relative;
  left: 50%;
  transform: translate(-50%);
}

.user-information .button:hover {
  background-color: rgb(0, 110, 255);
  color: #fff;
}
</style>
