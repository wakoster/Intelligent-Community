<template>
  <div class="homePage">
    <div class="topbox">
      <div class="name-wrap" onclick="document.querySelector('.name-wrap').classList.toggle('active')" tabindex="0" onblur="document.querySelector('.name-wrap.active') !== null?document.querySelector('.name-wrap.active').classList.toggle('active'):'无事发生'">
        <span class="name">{{this.userName}}</span>
        <div class="el-icon"></div>
        <div class="info-area">
          <ul>
            <li @click="skipSettingPage">
              <span class="iconfont">&#xe633;</span>
              <span>设置管理</span>
            </li>
            <li @click="logout">
              <span class="iconfont">&#xe75d;</span>
              <span>退出</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="tab-bar">
      <div class="tabBar-box" :class="{active: code === '0x0000'}" @click="code = '0x0000'">
        <label>导航页面</label>
      </div>
      <div class="tabBar-box" :class="{active: items.code === code}" @click="code = items.code" v-for="(items,index) in tabBar" :key="items.code">
        <label>{{items.title}}</label>
        <span class="iconfont" @click.stop="closeTabBar(index)">&#xe61d;</span>
      </div>
    </div>
    <div class="chooseIframe" :class="{hidden: code !== '0x0000'}">
      <div class="sites-list" v-for="items in pageList.data" :key="items.sort">
        <div class="label" v-if="items.sort !== '默认'">
          <span class="iconfont">&#xe87d;</span>
          {{items.sort}}
        </div>
        <div class="sites">
          <div class="sites-box" v-for="sites in items.list" :key="sites.code" @click="openTabBar(sites)">
            <div class="sites-info">
              <img class="img" :src='sites.img'>
              <div class="label-box">
                <label class="title">{{sites.title}}</label>
                <label class="description">{{sites.description}}</label>
              </div>
            </div>
            <span class="tooltiptext" v-if="sites.information !== null">{{sites.information}}</span>
          </div>
        </div>
      </div>
    </div>
    <iframe class="iframe" :class="{hidden: items.code !== code}" :src="items.url" v-for="items in tabBar" :key="items.code"></iframe>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import errorMessage from '../errorMessage.vue'
export default {
  components: { errorMessage },
  name: 'homePage',
  data: function () {
    return {
      code: '0x0000',
      userName: '',
      tabBar: [
      ],
      pageList: {}
    }
  },
  mounted: function () {
    // 获取页面标签
    this.getPageTag()
    this.userName = this.$cookies.get('cookie_username')
  },
  methods: {
    closeTabBar (index) {
      this.tabBar.splice(index, 1)
      this.code = '0x0000'
    },
    openTabBar (item) {
      if (!this.tabBar.includes(item)) {
        this.tabBar.push(item)
      }
      this.code = item.code
    },
    getPageTag () {
      // 页面标签
      this.$axios
        .get('/pageTag/getAllPageTag')
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.pageList = data
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    logout () {
      this.$axios.get('/logout')
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.$router.push('/login')
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
    },
    skipSettingPage () {
      this.$router.push('/settingPage')
    }
  }
}

</script>

<style scoped="scoped">
@font-face {
  font-family: 'iconfont';  /* Project id 3033470 */
  src: url('//at.alicdn.com/t/font_3033470_5tpgbtlxvky.woff2?t=1644130815015') format('woff2'),
       url('//at.alicdn.com/t/font_3033470_5tpgbtlxvky.woff?t=1644130815015') format('woff'),
       url('//at.alicdn.com/t/font_3033470_5tpgbtlxvky.ttf?t=1644130815015') format('truetype');
}

.iconfont{
  font-family: "iconfont" !important;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}

.homePage{
  width: 100%;
  height: 100%;
  position: absolute;
  overflow: hidden;/* 禁止页面滚动 */
}

.homePage .topbox{
  width: 100%;
  height: 40px;
  background-color: rgb(40, 140, 227);
}

.topbox .name-wrap{
  position: absolute;
  right: 20px;
  height: 40px;
  margin-right: 25px;
  cursor: pointer;
  display: flex;
}

.topbox .name{
  font-family: Microsoft Yahei;
  font-size: 18px;
  color: #fff;
  align-self: center;
}

.topbox .el-icon{
  position: relative;
  height: 20px;
  align-self: center;
  margin-left: 5px;
}

.topbox .el-icon:after,.topbox .el-icon:before{
  border: 8px solid transparent;
  border-top: 8px solid rgb(40, 140, 227);
  position: absolute;
  top: 4px;
  content: ' ';
}

.topbox .el-icon:before {
  border-top-color: #fff;
  top: 7px;
}

.name-wrap.active .el-icon:after,.name-wrap.active .el-icon:before{
  border: 8px solid transparent;
  border-bottom: 8px solid #fff;
  position: absolute;
  top: 0px;
  content: ' ';
}

.name-wrap.active .el-icon::after {
  border-bottom-color: rgb(40, 140, 227);
  top: 3px;
}

.name-wrap .info-area{
  display: none;
  position: absolute;
  top: 50px;
  right: 0px;
  transform: translateX(30%);
  font-size: 16px;
  background: #fff;
  color: #606266;
  line-height: 40px;
  width: 110px;
  border-radius: 5px;
  z-index: 1;
}

.name-wrap.active .info-area{
  display: block;
}

.info-area li{
  list-style-type:none;
  border-radius: 5px;
  display:flex;
  padding: 0px 10px;
}

.info-area li:hover{
  background-color: #f6f7f8;
}

.info-area .iconfont{
  position: relative;
  top: 3px;
}

.info-area span{
  position: relative;
}

.homePage .tab-bar{
  width: 100%;
  height: 25px;
  display: flex;
}

.homePage .tabBar-box{
  max-width: 200px;
  min-width: 60px;
  width: 100%;
  height: 100%;
  background-color: transparent;
  border-left: 5px;
  font-size: 8px;
  white-space: nowrap;
  display: flex;
  align-items: center;
  position: relative;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.tabBar-box label{
  margin-left: 10px;
  margin-right: 25px;
  overflow: hidden;
  text-overflow:ellipsis;
}

.tabBar-box span{
  font-size: 12px;
  color: rgb(117,117,117);
}

.homePage .tabBar-box.active,.homePage .tabBar-box:hover{
  background-color: rgb(208,228,255);
}

.homePage .tabBar-box.active::before,.homePage .tabBar-box:hover:before{
  content: '';
  position: absolute;
  top: 7px;
  right: -18px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  box-shadow: -10px 10px 0 rgb(208,228,255);
}

.homePage .tabBar-box.active::after,.homePage .tabBar-box:hover:after{
  content: '';
  position: absolute;
  top: 7px;
  left: -18px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  box-shadow: 10px 10px 0 rgb(208,228,255);
}

.tabBar-box .iconfont{
  position: absolute;
  right: 10px;
  display: none;
  cursor: pointer;
}

.tabBar-box.active .iconfont{
  display: flex;
}

.homePage .chooseIframe,.homePage .iframe{
  width: 100%;
  height: calc(100% - 65px);
  border-width: 0px;
  position: fixed;
  bottom: 0px;
  background-color: rgb(208,228,255);
}

.homePage .chooseIframe{
  background-color: rgb(208,228,255);
  overflow: auto;
}

.homePage .chooseIframe::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}

.homePage .chooseIframe::-webkit-scrollbar-thumb {
  background: #8d8d8d;
  border-radius: 10px;
}

.homePage .chooseIframe::-webkit-scrollbar-track-piece {
  background: transparent;
}

.homePage .chooseIframe.hidden,.homePage .iframe.hidden{
  display: none;
}

.chooseIframe .sites-list{
  position: relative;
  width: calc(100% - 60px);
  margin: 30px 30px 0px 30px;
}

.chooseIframe .label{
  color: #555;
  font-size: 18px;
  font-family: inherit;
  font-weight: 500;
  line-height: 1.1;
  margin-left: 20px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow:ellipsis;
}

.chooseIframe .sites{
  display:flex;
  flex-wrap: wrap;
  margin: 10px 0px;
  width: 100%;
}

.chooseIframe .sites-box{
  position: relative;
  height: 72px;
  width: 22%;
  min-width: 200px;
  margin: 20px 15px 0px 15px;
}

.chooseIframe .sites-info{
  position: relative;
  height: 100%;
  width: 100%;
  background-color: rgb(246,246,246);;
  border-radius: 4px;
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: 0.5s ease-in-out;
}

.chooseIframe .sites-info:hover{
  transform: translateY(-10%);
  box-shadow: 0px 5px 10px rgba(161, 161, 161, 0.4);
}

.sites-info .img{
  position: relative;
  width: 40px;
  height: 40px;
  margin: 15px;
}

.sites-info .label-box{
  width: calc(100% - 85px);
  height: 100%;
  margin-right: 15px;
  position: relative;
  display: flex;
  flex-wrap: wrap;
}

.label-box label{
  width: 100%;
  height: 50%;
  white-space:nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
  font-family: sans-serif;
  flex-shrink: 0;/* 防止宽度压缩 */
  cursor: pointer;
}

.label-box .title{
  font-weight: 700;
  position: relative;
  top: 15px;
}

.label-box .description{
  color: #878888;
  position: relative;
  top: 5px;
  font-size: 12px;
}

.sites-box .tooltiptext {
  visibility: hidden;
  background-color: rgba(0, 0, 0, 0.9);
  color: #fff;
  text-align: center;
  padding: 5px 0;
  border-radius: 4px;
  position: absolute;
  min-width: 190px;
  top: calc(100% + 5px);
  left: 50%;
  transform: translateX(-50%);
  font-size: 12px;
  padding: 5px;
  z-index: 1;
}

.sites-box:hover .tooltiptext {
  visibility: visible;
}

.sites-box .tooltiptext::after {
    content: "";
    position: absolute;
    bottom: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: transparent transparent rgba(0, 0, 0, 0.9) transparent;
}

.tooltiptext label{
  width: calc(100% - 40px);
  margin: 20px;
  line-height: 130%
}
</style>
