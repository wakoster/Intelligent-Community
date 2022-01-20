<template>
  <div class="pageBox">
    <div class="index-box">
      <div class="con-box left">
        <h2><span>管理系统</span>登录</h2>
        <p>请使用管理员账号进行登录</p>
        <img src="../../assets/logo.png" />
        <button @click="settingPageHiddenActive">切换至应用系统</button>
      </div>
      <div class="con-box right">
        <h2><span>应用系统</span>登录</h2>
        <p>初始密码为电话号码后四位</p>
        <img src="../../assets/logo.png" />
        <button @click="homePageHiddenActive">切换至管理系统</button>
      </div>
      <div class="form-box" id='form-box'>
        <div class="settingPage-box hidden" id='settingPage-box'>
          <h1>settingPage</h1>
          <input type="text" placeholder="用户名" />
          <input type="password" placeholder="密码" />
          <button @click="settingPage">登录</button>
        </div>
        <div class="homePage-box" id='homePage-box'>
          <h1>homePage</h1>
          <input type="text" placeholder="用户名" />
          <input type="password" placeholder="密码" />
          <button @click="homePage">登录</button>
        </div>
      </div>
    </div>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import errorMessage from '../errorMessage.vue'
export default {
  components: { errorMessage },
  name: 'login',
  mounted: function () {
    // 判断是否是要登录管理界面
    if (this.$route.query.active === 'settingPage') {
      this.homePageHiddenActive()
    }
  },
  methods: {
    homePageHiddenActive () {
      document.getElementById('homePage-box').classList.add('hidden')
      document.getElementById('settingPage-box').classList.remove('hidden')
      document.getElementById('form-box').style.transform = 'translateX(80%)'
    },
    settingPageHiddenActive () {
      document.getElementById('homePage-box').classList.remove('hidden')
      document.getElementById('settingPage-box').classList.add('hidden')
      document.getElementById('form-box').style.transform = 'translateX(0%)'
    },
    homePage () {
      this.$router.push('/homePage')
    },
    settingPage () {
      this.$router.push('/settingPagePage')
    },
    showErrorMessage (errorMessage) {
      this.$refs.errorMessage.setErrorMessage(errorMessage)
    }
  }
}
</script>

<style scoped="scoped">
.pageBox {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.index-box{
  background: white;
  width: 650px;
  height: 415px;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgba(0,0,0,0.1);
  position: relative;
}

.form-box{
  position: absolute;
  top: -10%;
  left: 5%;
  background-color: rgb(165, 180, 255);
  width: 320px;
  height: 500px;
  border-radius: 5px;
  box-shadow: 2px 0 10px rgba(0,0,0,0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.5s ease-in-out;
}

.settingPage-box,.homePage-box{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.hidden{
  display: none;
  transition: 0.5s;
}

h1{
  text-align: center;
  margin-bottom: 25px;
  text-transform: uppercase;
  color: #fff;
  letter-spacing: 5px;
}

input{
  background-color:transparent;
  width: 70%;
  color: #fff;
  border: none;
  border-bottom: 1px solid rgba(255,255,255,0.4);
  padding: 10px 0;
  text-indent: 10px;
  margin:  8px 0;
  font-size: 14px;
  letter-spacing: 2px;
}

input::placeholder{
  color: #fff;
}

input:focus{
  color: rgb(80, 110, 255);
  outline: none;
  border-bottom: 1px solid rgb(80, 110, 255);
  transition: 0.5s;
}

input:focus::placeholder{
  opacity: 0;
}

.form-box button{
  width: 70%;
  margin-top: 35px;
  background-color: #f6f6f6;
  outline: none;
  border-radius: 8px;
  padding: 13px;
  color: rgb(94,94,94);
  letter-spacing: 2px;
  border: none;
  cursor: pointer;
}

.form-box button:hover{
  background-color: rgb(80, 110, 255);
  color: #f6f6f6;
  transition: background-color 0.5s ease;
}

.con-box{
  width:50%;
  display: flex;
  flex-direction: column;
  justify-self: center;
  align-items: center;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.con-box.left{
  left: -2%
}
.con-box.right{
  right: -2%
}

.con-box h2{
  color: #8e9aaf;
  font-size: 25px;
  font-weight: bold;
  letter-spacing: 3px;
  text-align: center;
  margin-bottom: 4px;
}

.con-box p{
  font-size: 12px;
  letter-spacing: 2px;
  color: #8e9aaf;
  text-align: center;
}

.con-box span{
  color: rgb(165, 180, 255);
}

.con-box img {
  width: 150px;
  height: 150px;
  opacity: 0.9;
  margin: 40px 0;
}

.con-box button{
  margin-top: 3%;
  background-color: #fff;
  color: rgb(94,94,94);
  border: 1px solid rgb(165, 180, 255);
  padding: 6px 10px;
  border-radius: 5px;
  letter-spacing: 1px;
  outline: none;
  cursor: pointer;
}

.con-box button:hover{
  background-color: rgb(80, 110, 255);
  color: #fff;
}
</style>
