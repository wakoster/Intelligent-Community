<template>
  <div class="homePage">
    <div class="topbox"></div>
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
      <div class="sites-list" v-for="items in pageList.list" :key="items.sort">
        <div class="label">
          <span class="iconfont">&#xe87d;</span>
          {{items.sort}}
        </div>
        <div class="sites">
          <div class="sites-box" v-for="sites in items.list" :key="sites.code" @click="openTabBar(sites)">
            <div class="sites-info">
              <!-- <img class="img" :src='sites.img'> -->
              <img class="img" src='../../assets/logo.png'>
              <div class="label-box">
                <label class="title">{{sites.title}}</label>
                <label class="description">{{sites.description}}</label>
              </div>
            </div>
            <span class="tooltiptext">{{sites.description}}</span>
          </div>
        </div>
      </div>
    </div>
    <iframe class="iframe" :class="{hidden: items.code !== code}" :src="items.url" v-for="items in tabBar" :key="items.code"></iframe>
  </div>
</template>

<script>
export default {
  name: 'homePage',
  data: function () {
    return {
      code: '0x0000',
      tabBar: [
      ],
      pageList: {
        code: 0,
        msg: '成功!',
        total: 2,
        list: [
          {
            sort: '交流',
            list: [
              {
                code: '0x5669',
                title: '聊天界面',
                url: 'http://localhost:8081/chatSystem/chat',
                img: '',
                description: '聊天'
              },
              {
                code: '0x5671',
                title: '聊天界面',
                url: 'http://localhost:8081/chatSystem/userInfo',
                img: '',
                description: '聊天聊天界面聊天界面聊天界面聊天界面聊天界面聊天界面聊天界面聊天界面'
              }
            ]
          },
          {
            sort: '交流2',
            list: [
              {
                code: '0x5670',
                title: '个人信息',
                url: 'http://localhost:8080/login',
                img: '',
                description: '个人信息'
              }
            ]
          }
        ]
      }
    }
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
    }
  }
}

</script>

<style scoped="scoped">
@font-face {
  font-family: 'iconfont';  /* Project id 3033470 */
  src: url('//at.alicdn.com/t/font_3033470_ipppo9uuask.woff2?t=1642487649106') format('woff2'),
       url('//at.alicdn.com/t/font_3033470_ipppo9uuask.woff?t=1642487649106') format('woff'),
       url('//at.alicdn.com/t/font_3033470_ipppo9uuask.ttf?t=1642487649106') format('truetype');
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
  background-color: rgb(0,110,255);
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
  background-color: rgba(0,110,255,0.098);
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
}

.homePage .chooseIframe{
  background-color: rgba(0,110,255,0.098);
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
