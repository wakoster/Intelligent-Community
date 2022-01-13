<template>
  <div class="homePage"><!-- 主页根节点 -->
    <router-view />
    <div class="navigationBar"><!-- 左侧导航栏 -->
      <ul>
        <li class="navigation-list" :class="{active: this.$route.path == '/chatSystem/chat'}">
          <router-link class="router-link" to="/chatSystem/chat"><!-- 聊天跳转按钮 -->
            <span class="iconfont">&#xe63f;</span>
            <span class="iconfont-text">聊天</span>
          </router-link>
        </li>
        <li class="navigation-list" :class="{active: this.$route.path == '/chatSystem/contacts'}">
          <router-link class="router-link"  to="/chatSystem/contacts"><!-- 通讯录跳转按钮 -->
            <span class="iconfont">&#xe677;</span>
            <span class="iconfont-text">通讯录</span>
          </router-link>
        </li>
        <li class="navigation-list" :class="{active: this.$route.path == '/chatSystem/userInfo'}">
          <router-link class="router-link"  to="/chatSystem/userInfo"><!-- 个人信息跳转按钮 -->
            <span class="iconfont">&#xe643;</span>
            <span class="iconfont-text">个人信息</span>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Chat from './chat/index.vue'
export default {
  components: { Chat },
  name: 'homePage'
}

</script>

<style scoped="scoped">
@font-face {
  /* 阿里矢量图标库font-face */
  font-family: 'iconfont';  /* Project id 3033470 */
  src: url('//at.alicdn.com/t/font_3033470_z350dp2cjq8.woff2?t=1641631028751') format('woff2'),
       url('//at.alicdn.com/t/font_3033470_z350dp2cjq8.woff?t=1641631028751') format('woff'),
       url('//at.alicdn.com/t/font_3033470_z350dp2cjq8.ttf?t=1641631028751') format('truetype');
}

.iconfont,
.line {
  font-family: "iconfont" !important;
  font-size: 20px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}

.navigationBar{
  position: absolute;
  height: 100%;
  width: 50px;
  background: #2b343b;
  box-shadow: 10px 0 0 #4187f6;
  overflow-x: hidden;
  transition: width 0.25s;
}

.navigationBar::-webkit-scrollbar {
  display: none;
}

.navigationBar:hover{
  width: 200px;
}

.navigationBar ul{
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  padding-left: 5px;
  padding-top: 40px;
}

.navigation-list{
  position: relative;
  list-style: none;
  width: 100%;
  border-top-left-radius: 12px;
  border-bottom-left-radius: 12px;
}

.navigation-list.active{
  background: #4187f6;
}

.navigation-list .router-link{
  position: relative;
  display: block;
  width: 100%;
  height: 50px;
  display: flex;
  text-decoration: none;
  color: #fff;
}

.navigation-list.active .router-link::before{
  content: '';
  position: absolute;
  top: -30px;
  right: 5px;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  box-shadow: 15px 15px 0 #4187f6;
}

.navigation-list.active .router-link::after{
  content: '';
  position: absolute;
  bottom: -30px;
  right: 5px;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  box-shadow: 15px -15px 0 #4187f6;
}

.iconfont{
  position: relative;
  display: block;
  min-width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
}

.iconfont-text{
  position: relative;
  display: block;
  padding-left: 10px;
  height: 60px;
  line-height: 50px;
  white-space: nowrap;
}
</style>
