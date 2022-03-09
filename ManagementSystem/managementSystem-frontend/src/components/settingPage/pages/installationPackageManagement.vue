<template>
  <div class="installationPackageManagement">
    <div class="header">
      <div class="blogTitle">
        <h1 class="title">安装管理</h1>
      </div>
      <div class="navigator">
        <ul class="navList">
          <li><div @click="changeInstallationPackageScreeningCondition(null,null)">全部</div></li>
          <li><div @click="changeInstallationPackageScreeningCondition('已安装',null)">已安装</div></li>
          <li><div @click="changeInstallationPackageScreeningCondition('未安装',null)">未安装</div></li>
          <li><div @click="changeInstallationPackageScreeningCondition(null,true)">标记</div></li>
          <li><div style="padding-top: 10px;"><button class="button">上传</button></div></li>
        </ul>
      </div>
    </div>
    <div class="box">
      <div class="left-box">
        <div class="installationPackage">
          <div class="installationPackageBlock" v-for="item in installationPackage.list" :key="item.id">
            <div class="selectBlock" v-if="(installationPackageScreeningCondition.sign == null || installationPackageScreeningCondition.sign == item.sign) && (installationPackageScreeningCondition.state == null || installationPackageScreeningCondition.state == item.state)">
              <div class="name" @click="on_off_configuration">{{item.name}}</div>
              <div class="versions">{{item.versions}}</div>
              <div class="operatingState">{{item.operatingState}}</div>
              <div class="operationBox">
                <div class="operation" :class="{active: item.state == '未安装'}" @click="test">安装</div>
                <div class="operation" :class="{active: item.state == '已安装'}" @click="test">卸载</div>
                <div class="operation" :class="{active: item.state == '未安装'}" @click="test">删除</div>
                <div class="operation" :class="{active: item.operatingState != '正常运行'}" @click="test">启动</div>
                <div class="operation" :class="{active: item.operatingState == '正常运行'}" @click="test">停止</div>
              </div>
              <div class="sign" v-if="item.sign" @click="sign(item.id),item.sign = !item.sign"><span class="iconfont">&#xe707;</span></div>
              <div class="sign" v-if="!item.sign" @click="sign(item.id),item.sign = !item.sign"><span class="iconfont">&#xe631;</span></div>
            </div>
          </div>
        </div>
      </div>
      <div class="right-box">
        <div class="operatingRecord">
          <div class="operatingRecord-header">
            <h3>安装记录</h3>
            <span class="iconfont">&#xe8c8;</span>
          </div>
          <div class="operatingRecordBlock" v-if="updateNow !== null">
            <div class="record-type">{{updateNow.type}}</div>
            <div class="record-name">{{updateNow.name}}</div>
            <div class="container">
              <div class="skills" :style="'width:' + updateNow.schedule + '%'">{{updateNow.schedule}}%</div>
            </div>
            <div class="record-time">{{updateNow.time}}</div>
          </div>
          <div class="operatingRecordBlock" v-for="item in operatingRecord.list" :key="item.id">
            <div class="record-type">{{item.type}}</div>
            <div class="record-name">{{item.name}}</div>
            <div class="record-info">{{item.info}}</div>
            <div class="record-time">{{formatDate(item.time)}}</div>
            <span class="iconfont">&#xe617;</span>
          </div>
          <div class="more" onclick="document.querySelector('.more').classList.add('hidden')">更多</div>
        </div>
      </div>
      <div class="configuration">
        <div class="mask"></div>
        <div class="pop">
          <div class="poptitle">新建工单</div>
          <div class="formitem">
            <span>*</span>
            <label>工单分类</label>
            <div class="inputBox"><input placeholder="请输入工单标题"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>工单分类</label>
            <div class="inputBox"><input placeholder="请输入工单标题"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>工单分类</label>
            <div class="inputBox"><input placeholder="请输入工单标题"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>工单分类</label>
            <div class="inputBox"><input placeholder="请输入工单标题"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>工单分类</label>
            <div class="inputBox"><input placeholder="请输入工单标题"></div>
          </div>
          <button class="button-close" @click="on_off_configuration">关闭</button>
          <button class="button-verify" @click="on_off_configuration">确认</button>
        </div>
      </div>
    </div>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import {getDate} from '../../../common/date.js'
import errorMessage from '../../errorMessage.vue'
export default {
  components: { errorMessage },
  name: 'installationPackageManagement',
  data: function () {
    return {
      updateNow: {
        name: 'chatSystem',
        type: '正在安装',
        time: '2022-02-14 10:28',
        schedule: 66
      },
      operatingRecord: {
        code: 0,
        msg: '成功!',
        total: 2,
        list: [
          {
            id: 1,
            name: 'chatSystem',
            type: '安装成功',
            time: 1646809494,
            info: ''
          },
          {
            id: 2,
            name: 'chatSystem',
            type: '安装失败',
            time: 1646809494000,
            info: '安装空间不足'
          }
        ]
      },
      installationPackage: {
        code: 0,
        msg: '成功!',
        total: 2,
        list: [
          {
            id: 1,
            name: 'chatSystem',
            versions: '1.0.0',
            state: '已安装',
            operatingState: '正常运行',
            sign: true
          },
          {
            id: 2,
            name: 'facesSystem',
            versions: '1.0.0',
            state: '未安装',
            operatingState: '已停止',
            sign: false
          }
        ]
      },
      installationPackageScreeningCondition: {
        state: null,
        sign: null
      }
    }
  },
  methods: {
    changeInstallationPackageScreeningCondition (state, sign) {
      this.installationPackageScreeningCondition.state = state
      this.installationPackageScreeningCondition.sign = sign
    },
    sign (id) {
    },
    on_off_configuration () {
      document.querySelector('.configuration .pop').classList.toggle('active')
      document.querySelector('.configuration .mask').classList.toggle('active')
    },
    formatDate (time) {
      return getDate(time.toString(), 'yyyy-MM-dd hh:mm:ss')
    },
    showErrorMessage (errorMessage) {
      this.$refs.errorMessage.setErrorMessage(errorMessage)
    }
  }
}
</script>

<style scoped="scoped">
@font-face {
  font-family: 'iconfont';  /* Project id 3033470 */
  src: url('//at.alicdn.com/t/font_3033470_h943ij4g0q.woff2?t=1645004603567') format('woff2'),
       url('//at.alicdn.com/t/font_3033470_h943ij4g0q.woff?t=1645004603567') format('woff'),
       url('//at.alicdn.com/t/font_3033470_h943ij4g0q.ttf?t=1645004603567') format('truetype');
}

.iconfont{
  font-family: "iconfont" !important;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
}

.installationPackageManagement {
  position: relative;
  width: 100%;
  min-height: 450px;
}

.header .blogTitle {
  height: 60px;
  clear: both;
}

.blogTitle .title {
  font-size: 30px;
  font-weight: bold;
  line-height: 60px;
  margin-top: 20px;
  color: #515151;
}

.header .navigator {
  font-size: 16px;
  border-bottom: 1px solid #ededed;
  border-top: 1px solid #ededed;
  height: 50px;
  clear: both;
  margin-top: 25px;
}

.navigator .navList {
  min-height: 30px;
  float: left;
}

.navList li {
  float: left;
  margin: 0 50px 0 0;
  list-style: none;
}

.navList li div {
  color: #6a6a6a;
  font-weight: bold;
  display: block;
  width: 5em;
  height: 22px;
  float: left;
  text-align: center;
  padding-top: 15px;
}

.navList li div:hover {
  color: #000;
  cursor: pointer;
}

.navList .button {
  width: 80px;
  height: 30px;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
  position: absolute;
  right: 50px;
}

.navList .button:hover {
  background-color: rgb(0, 110, 255);
  color: #fff;
}

.box {
  display: flex;
}

.box .left-box {
  position: relative;
  left: 0px;
  width: 70%;
  min-width: 700px;
}

.left-box .installationPackage{
  margin: 15px;
  word-wrap: break-word;
}

.installationPackageBlock .selectBlock{
  display: flex;
  line-height: 30px;
  border-bottom: 1px solid #ededed;
  color: #666;
}

.installationPackage .name{
  position: absolute;
  left: 15px;
  font-size: 16px;
  color: #000;
}

.installationPackage .name:hover{
  cursor: pointer;
  color: #006eff;
}

.installationPackage .versions{
  position: absolute;
  left: 200px;
  font-size: 12px;
}

.installationPackage .operatingState{
  position: relative;
  left: 300px;
}

.installationPackage .operationBox{
  position:absolute;
  right: 80px;
  display: flex;
}

.installationPackage .operation{
  margin: 0 10px;
  pointer-events: none;
}

.installationPackage .operation.active{
  pointer-events: auto;
  cursor: pointer;
  color: #006eff;
}

.installationPackage .sign{
  position:absolute;
  right: 25px;
}

.box .right-box {
  position: relative;
  right: 0px;
  width: 30%;
  min-width: 200px;
}

.right-box .operatingRecord{
  margin: 15px;
  word-wrap: break-word;
}

.operatingRecord h3{
  font-size: 18px;
  text-align: left;
  margin-bottom: 20px;
}

.operatingRecord .operatingRecord-header{
  display: flex;
}

.operatingRecord-header .iconfont{
  position: absolute;
  right: 0px;
  align-self:center;
  color: #757575;
}

.operatingRecord .operatingRecordBlock{
  height: 80px;
  position: relative;
  font-size: 12px;
}

.operatingRecordBlock .container {
  width: 80%;
  height: 15px;
  background-color: #ddd;
  margin: 5px auto;
}

.container .skills {
  text-align: right;
  line-height: 15px;
  width: 0%;
  background-color: rgb(115, 145, 255);
  color: white;
  white-space:nowrap;
}

.operatingRecordBlock .record-type{
  font-size: 14px;
}

.operatingRecordBlock .record-name{
  color: rgb(155,155,155);
  margin-left: 15px;
}

.operatingRecordBlock .record-info{
  text-align: right;
  position: absolute;
  right: 12px;
  bottom: 16px;
}

.operatingRecordBlock .record-time {
  text-align: right;
  position: absolute;
  right: 12px;
  bottom: 4px;
  color: rgb(155,155,155);
}

.operatingRecordBlock .iconfont{
  position: absolute;
  right: -12px;
  bottom: 5px;
  font-size: 6px;
  color: rgb(216,216,216);
}

.operatingRecord .more{
  margin-top: 15px;
  color: #006eff;
  transform: translateX(50%);
  cursor:pointer;
  font-size: 6px;
}

.operatingRecord .more.hidden{
  display: none;
}

.configuration .mask{
  background-color: #000;
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: none;
}

.configuration .pop{
  width: 500px;
  height: 600px;
  background-color: #FFFFFF;
  border-radius: 4px;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  display: none;
}

.configuration .mask.active,.configuration .pop.active{
  display: block;
}

.configuration .poptitle{
  font-size: 16px;
  font-weight: 400;
  color: #333333;
  line-height: normal;
  font-feature-settings: "kern";
  margin: 20px;
}

.configuration .formitem{
  height: 40px;
  margin-top: 40px;
  margin-left: 20px;
  display: flex;
}

.formitem span{
  color:#ff0000;
  align-self:center;
}

.formitem label{
  align-self:center;
}

.formitem .inputBox{
  width: 300px;
  height: 40px;
  position:fixed;
  right: 50px;
  margin-left: 50px;
  background-color: rgb(242,242,242);
  display: inline-flex;
  align-items: center;
  border-radius: 2px;
  align-self:center;
}

.formitem input{
  width: 300px;
  height: 25px;
  position: relative;
  left: 10px;
  background-color: transparent;
  border:none;
  outline:none;
  font-size: 12px;
}

.configuration .button-close{
  position: absolute;
  left:398px;
  top:554px;
}

.configuration .button-verify{
  position: absolute;
  left:305px;
  top:554px;
}

.configuration .button-verify,.configuration .button-close{
  width: 80px;
  height: 30px;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
}

.configuration .button-verify:hover,.configuration .button-close:hover{
  background-color: rgb(0, 110, 255);
  color: #fff;
}
</style>
