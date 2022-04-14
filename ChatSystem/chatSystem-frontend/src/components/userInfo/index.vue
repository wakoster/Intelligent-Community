<template>
  <div class="userInfo">
    <div class="information">
      <label>基本信息</label>
      <div class="informationBox">
        <div>上传头像:</div>
        <div class="updateImgBox" :class="{active: userInfo.userImg !== null}">
          <img class="preview" :src="userInfo.userImg" v-if="userInfo.userImg !== null">
          <label for="upload" v-if="userInfo.userImg === null">上传</label>
          <input class="upload" id="upload" @change="userImgUpload($event)" ref="upload" type="file"/>
          <div class="clearImg-box" v-if="userInfo.userImg !== null">
            <div class="clearImg" @click="userInfo.userImg = null
            $refs.upload.value = null">
              <span class="iconfont">&#xe8b6;</span>
            </div>
          </div>
        </div>
        <div class="reminder">图片大小请限制在1M以下</div>
      </div>
      <div class="informationBox">
        <div>邮箱地址:</div>
        <div class="inputBox">
          <input type="email" @focus="activeLink($event)" @blur="clearActive()" placeholder="请填写邮箱以接受系统邮件" v-model="userInfo.mailbox">
        </div>
      </div>
      <div class="informationBox">
        <div>所在地区:</div>
        <div class="inputBox">
          <input type="text" @focus="activeLink($event)" @blur="clearActive()" placeholder="请填写所在地区" v-model="userInfo.address">
        </div>
      </div>
      <div class="informationBox">
        <div>个人签名:</div>
        <div class="inputBox">
          <input type="text" @focus="activeLink($event)" @blur="clearActive()" placeholder="请填写个人签名" v-model="userInfo.sign">
        </div>
      </div>
      <button class="button" type="submit" @click="changeInformation">提交修改</button>
      <div class="line"></div>
      <label>密码设置</label>
      <div class="informationBox">
        <div>旧密码:</div>
        <div class="inputBox">
          <input type="password" @focus="activeLink($event)" @blur="clearActive()" placeholder="请输入原密码" v-model="password.oldPassword">
        </div>
      </div>
      <div class="informationBox">
        <div>新密码:</div>
        <div class="inputBox">
          <input type="password" @focus="activeLink($event)" @blur="clearActive()" placeholder="请输入新密码" v-model="password.newPassword">
        </div>
      </div>
      <div class="informationBox">
        <div>确认新密码:</div>
        <div class="inputBox">
          <input type="password" @focus="activeLink($event)" @blur="clearActive()" placeholder="请确认新密码" v-model="password.newPasswordAgain">
        </div>
        <div class="errorMessage" :class="{active: password.newPassword === password.newPasswordAgain}">两次输入不一致</div>
      </div>
      <button class="button" type="submit" @click="changePassword">修改密码</button>
    </div>
    <message ref="message"></message>
  </div>
</template>

<script>
import message from '../message.vue'
export default {
  components: { message },
  name: 'UserInfo',
  data: function () {
    return {
      userInfo: {
        userImg: null,
        mailbox: null,
        address: null,
        sign: null
      },
      password: {
        oldPassword: null,
        newPassword: null,
        newPasswordAgain: null
      }
    }
  },
  mounted: function () {
    this.selectInformation()
  },
  methods: {
    activeLink (event) {
      // 给输入框挂载active
      document.querySelectorAll('.inputBox.active').forEach((item) =>
        item.classList.remove('active'))
      event.path[1].className += ' active'
    },
    clearActive (event) {
      // 清除输入框挂载的active
      document.querySelectorAll('.inputBox.active').forEach((item) =>
        item.classList.remove('active'))
    },
    clearBaseInformation () {
      this.userInfo.userImg = null
      this.userInfo.mailbox = null
      this.userInfo.phoneNumber = null
      this.userInfo.address = null
      this.userInfo.sign = null
    },
    clearPassword () {
      this.password.oldPassword = null
      this.password.newPassword = null
      this.password.newPasswordAgain = null
    },
    userImgUpload (el) {
      var img = el.target.files[0]
      if (img === undefined) {
        return null
      }
      var type = img.type
      if (type.split('/')[0] !== 'image') {
        this.showErrorMessage('请上传正确的图片文件')
        return null
      }
      if (img.size > 1024 * 1024 * 1) { // 1M
        this.showErrorMessage('文件大小超出限制')
        return null
      }
      var This = this
      var fileReader = new FileReader()
      fileReader.readAsDataURL(img)
      fileReader.onload = function () {
        This.userInfo.userImg = this.result
      }
    },
    changeInformation () {
      // 更改信息
      this.$axios
        .post('/user/changeInformation', {
          phoneNumber: this.$cookies.get('cookie_userPhoneNumber'),
          userImg: this.userInfo.userImg,
          mailbox: this.userInfo.mailbox,
          address: this.userInfo.address,
          sign: this.userInfo.sign
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.showInfoMessage('修改成功')
            this.selectInformation()
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    changePassword () {
      // 更改密码
      this.$axios
        .post('/user/changePassword', {
          phoneNumber: this.$cookies.get('cookie_userPhoneNumber'),
          oldPassword: this.$md5(this.password.oldPassword + 'KEY'),
          newPassword: this.password.newPassword
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.showInfoMessage('修改成功')
            this.clearPassword()
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    selectInformation () {
      // 查询信息
      this.$axios
        .post('/user/selectInformation', {
          phoneNumber: this.$cookies.get('cookie_userPhoneNumber')
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.userInfo = data.data
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    showErrorMessage (errorMessage) {
      this.$refs.message.setErrorMessage(errorMessage)
    },
    showInfoMessage (infoMessage) {
      this.$refs.message.setInfoMessage(infoMessage)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="scoped">
@font-face {
  font-family: 'iconfont';  /* Project id 3033470 */
  src: url('//at.alicdn.com/t/font_3033470_jn97wzrv2qk.woff2?t=1641736874589') format('woff2'),
       url('//at.alicdn.com/t/font_3033470_jn97wzrv2qk.woff?t=1641736874589') format('woff'),
       url('//at.alicdn.com/t/font_3033470_jn97wzrv2qk.ttf?t=1641736874589') format('truetype');
}

.iconfont{
  font-family: "iconfont" !important;
  font-size: 26px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}

.userInfo{
  width: calc(100% - 95px);
  height: calc(100% - 20px);
  position: absolute;
  left: 64px;
  background-color: #FFFFFF;
  border-radius: 4px;
  margin: 10px 0px 10px 15px;
  overflow: auto;
  /* line-height: 40px; */
}

.userInfo::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}

.userInfo::-webkit-scrollbar-thumb {
  background: #d8d8d8;
  border-radius: 10px;
}

.userInfo::-webkit-scrollbar-track-piece {
  background: transparent;
}

.userInfo .information{
  width: 458px;
  position: relative;
  top: 30px;
  display: flex;
  flex-flow: column;
  left: 50%;
  transform: translate(-50%);
}

.information .informationBox{
  min-height: 40px;
  position: relative;
  margin-top: 19px;
  display: inline-flex;
  align-items: center;
  color: rgba(153,153,153,0.847);
}

.informationBox .updateImgBox{
  border-radius: 4px;
  border: 1px dashed rgb(217,217,217);
  width: 104px;
  height: 104px;
  background-color: rgb(250,250,250);
  position: relative;
  left: 80px;
}

.informationBox .updateImgBox.active{
  color:transparent;
}

.updateImgBox label{
  cursor: pointer;
  width: 100%;
  height: 100%;
  position: relative;
  display: flex;
  align-items: center;
  background-color: transparent;
  justify-content: center;
}

.updateImgBox .preview{
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0px;
  border-radius: 4px;
}

.updateImgBox .upload{
  display: none;
}

.updateImgBox .clearImg-box{
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0px;
}

.updateImgBox .clearImg{
  position: absolute;
  width: 100%;
  height: 30%;
  bottom: 0px;
  background-color: transparent;
  color: transparent;
  border-radius: 0px 0px 4px 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.updateImgBox:hover .clearImg{
  background-color: rgba(217,217,217,0.9);
  color: rgba(117, 117, 117,0.9);
}

.informationBox .reminder{
  color: rgba(153,153,153,0.847);
  font-size: 10px;
  position: relative;
  right: -100px;
}

.informationBox .inputBox{
  width: 320px;
  height: 40px;
  position:fixed;
  right: 0px;
  margin-left: 50px;
  background-color: rgb(242,242,242);
  display: inline-flex;
  align-items: center;
  border-radius: 2px;
}

.informationBox input{
  width: 300px;
  height: 25px;
  position: relative;
  left: 10px;
  background-color: transparent;
  border:none;
  outline:none;
  font-size: 12px;
}

.informationBox .inputBox.active{
  background-color: transparent;
  border: 1px solid rgb(0,110,255);
}

.userInfo .button {
  width: 80px;
  height: 30px;
  background: inherit;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
  position: relative;
  margin: 20px;
  left: 50%;
  transform: translate(-50%);
}

.userInfo .button:hover {
  background-color: rgb(0, 110, 255);
  color: #fff;
}

.information .line{
  width: 100%;
  height: 2px;
  background-color: rgb(226,226,226);
  margin: 10px 0px 20px 0px;
}

.informationBox .errorMessage{
  color: #f00;
  font-size: 10px;
  position: fixed;
  right: -100px;
}

.informationBox .errorMessage.active{
  display: none;
}
</style>
