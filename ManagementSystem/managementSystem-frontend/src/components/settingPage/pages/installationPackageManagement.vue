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
          <li>
            <div class="upload-box">
              <label for="upload">上传</label>
              <input class="upload" id="upload" @change="uploadInstallationPackage($event)" ref="upload" type="file" onclick="this.uploadLoading = true"/>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="box">
      <div class="left-box">
        <div class="installationPackage">
          <div class="installationPackageBlock" v-for="(item,index) in installationPackage.data" :key="item.id">
            <div class="selectBlock" v-if="(installationPackageScreeningCondition.sign == null || installationPackageScreeningCondition.sign == item.sign) && (installationPackageScreeningCondition.state == null || installationPackageScreeningCondition.state == item.state)">
              <div class="name" @click="openPageTagInfo(item)">{{item.name}}</div>
              <div class="versions">{{item.versions}}</div>
              <div class="operatingState">{{item.operatingState}}</div>
              <div class="operationBox">
                <div class="operation" :class="{active: item.state == '未安装'}">安装</div>
                <div class="operation" :class="{active: item.state == '已安装'}">卸载</div>
                <div class="operation" :class="{active: item.state == '未安装'}" @click="deleteInstallationPackage(item,index)">删除</div>
                <div class="operation" :class="{active: item.operatingState != '正常运行' && item.state != '未安装'}">启动</div>
                <div class="operation" :class="{active: item.operatingState == '正常运行' && item.state != '未安装'}">停止</div>
              </div>
              <div class="sign" v-if="item.sign" @click="sign(item,index)"><span class="iconfont">&#xe707;</span></div>
              <div class="sign" v-if="!item.sign" @click="sign(item,index)"><span class="iconfont">&#xe631;</span></div>
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
          <div class="operatingRecordBlock" v-for="(item,index) in operatingRecord.data" :key="item.id">
            <div class="record-type">{{item.type}}</div>
            <div class="record-name">{{item.name}}</div>
            <div class="record-info">{{item.info}}</div>
            <div class="record-time">{{formatDate(item.time)}}</div>
            <span class="iconfont" @click="deleteOperatingRecord(item,index)">&#xe617;</span>
          </div>
          <div class="more" @click="getOperatingRecord()">更多</div>
        </div>
      </div>
      <div class="configuration">
        <div class="mask"></div>
        <div class="pop">
          <div class="poptitle">设置页面标签</div>
          <div class="formitem">
            <span>*</span>
            <label>标签标码</label>
            <div class="inputBox"><input placeholder="请输入标签标识码" v-model="pageTagInfo.code"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>标签标题</label>
            <div class="inputBox"><input placeholder="请输入标签标题" v-model="pageTagInfo.title"></div>
          </div>
          <div class="formitem">
            <span>*</span>
            <label>标签路径</label>
            <div class="inputBox"><input placeholder="请输入标签路径" v-model="pageTagInfo.url"></div>
          </div>
          <div class="formitem">
            <span>&nbsp;</span>
            <label>标签类别</label>
            <div class="inputBox" style="z-index: 1">
              <input placeholder="标签类别" v-model="pageTagInfo.sort" onfocus="document.querySelector('.formitem-select.hidden') !== null?document.querySelector('.formitem-select.hidden').classList.toggle('hidden'):'无事发生'" onblur="document.querySelector('.formitem-select').classList.toggle('hidden')">
              <div class="formitem-select hidden">
                <div v-for="item in pageTagSort.data" :key="item.id">
                  <div class="select" @mousedown="pageTagInfo.sort = item.sort">{{item.sort}}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="formitem-img">
            <span>&nbsp;</span>
            <div class="img-title">上传图像</div>
            <div class="updateImgBox" :class="{active: pageTagInfo.img !== null}">
              <img class="preview" :src="pageTagInfo.img" v-if="pageTagInfo.img !== null">
              <label for="uploadImg" v-if="pageTagInfo.img === null">上传</label>
              <input class="uploadImg" id="uploadImg" @change="pageTagInfoImgUpload($event)" ref="uploadImg" type="file"/>
              <div class="clearImg-box" v-if="pageTagInfo.img !== null">
                <div class="clearImg" @click="pageTagInfo.img = null
                $refs.uploadImg.value = null">
                  <span class="iconfont">&#xe8b6;</span>
                </div>
              </div>
            </div>
            <div class="reminder">图片大小请限制在1M以下</div>
          </div>
          <div class="formitem">
            <span>&nbsp;</span>
            <label>标签描述</label>
            <div class="inputBox"><input placeholder="请输入标签描述" v-model="pageTagInfo.description"></div>
          </div>
          <div class="formitem">
            <span>&nbsp;</span>
            <label>详细信息</label>
            <div class="inputBox"><input placeholder="请输入标签详细信息" v-model="pageTagInfo.information"></div>
          </div>
          <button class="button-close" @click="closePageTagInfo">关闭</button>
          <button class="button-verify" @click="submitPageTagInfo">确认</button>
        </div>
      </div>
      <div class="uploadLoading" v-if="this.uploadLoading">
        <div class="uploadLoading-mask"></div>
        <loading></loading>
      </div>
    </div>
    <error-message ref="errorMessage"></error-message>
  </div>
</template>

<script>
import {getDate} from '../../../common/date.js'
import errorMessage from '../../errorMessage.vue'
import Loading from '../../loading.vue'
export default {
  components: { errorMessage, Loading },
  name: 'installationPackageManagement',
  data: function () {
    return {
      uploadLoading: false,
      pageTagInfo: {
        installationPackageId: null,
        img: null,
        code: null,
        title: null,
        url: null,
        description: null,
        information: null,
        sort: null
      },
      pageTagSort: {},
      updateNow: {
        name: 'chatSystem',
        type: '正在安装',
        time: '2022-02-14 10:28',
        schedule: 66
      },
      operatingRecord: {},
      operatingRecordRows: 10,
      installationPackage: {
        data: [
          {
            id: 17,
            name: 'chart',
            pageTagInfoId: null
          }
        ]
      },
      installationPackageScreeningCondition: {
        state: null,
        sign: null
      }
    }
  },
  mounted: function () {
    // 获取安装包信息
    this.getInstallationPackage()
    // 获取安装包操作记录
    this.getOperatingRecord()
    // 获取页面标签的分类信息
    this.getPageTagSort()
  },
  methods: {
    changeInstallationPackageScreeningCondition (state, sign) {
      this.installationPackageScreeningCondition.state = state
      this.installationPackageScreeningCondition.sign = sign
    },
    getInstallationPackage () {
      // 获取安装包信息
      this.$axios
        .get('/installationPackage/getAllInstallationPackage')
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.installationPackage = data
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    getOperatingRecord () {
      // 获取安装包操作记录
      var url = '/installationPackage/selectOperatingRecord?rows=' + this.operatingRecordRows
      this.$axios
        .get(url)
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.operatingRecord = data
            if (data.data.length < this.operatingRecordRows) {
              document.querySelector('.more').classList.add('hidden')
            } else {
              this.operatingRecordRows += 10
            }
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    sign (item, index) {
      // 修改关注信息
      this.$axios
        .post('/installationPackage/changeSignInfo', {
          installationPackageId: item.id,
          sign: !item.sign
        })
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.installationPackage.data[index].sign = !this.installationPackage.data[index].sign
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    deleteOperatingRecord (item, index) {
      // 删除安装包操作记录
      this.$axios
        .post('/installationPackage/deleteOperatingRecord', {id: item.id})
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.operatingRecord.data.splice(index, 1)
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    getPageTagSort () {
      // 获取页面标签的分类信息
      this.$axios
        .get('/pageTag/getPageTagSort')
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.pageTagSort = data
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    uploadInstallationPackage (el) {
      var file = el.target.files[0]
      if (file === undefined) {
        return null
      }
      if (file.name.split('.')[file.name.split('.').length - 1] !== 'jar') {
        this.showErrorMessage('请上传正确的jar包')
        return null
      }
      var formData = new FormData()
      formData.append('installationPackage', file)
      this.uploadLoading = true
      // 上传安装包文件
      this.$axios.post('/installationPackage/uploadInstallationPackage', formData)
        .then(resp => {
          let {
            data
          } = resp
          if (data.code !== 0) {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
        .finally(() => {
          el.target.value = null
          this.uploadLoading = false
          this.getInstallationPackage()
        })
    },
    deleteInstallationPackage (item, index) {
      // 删除安装包
      this.$axios
        .post('/installationPackage/deleteInstallationPackage', {id: item.id})
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.installationPackage.data.splice(index, 1)
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
    },
    pageTagInfoImgUpload (el) {
      // 页面标签图片上传
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
        This.pageTagInfo.img = this.result
      }
    },
    openPageTagInfo (item) {
      // 打开页面标签设置界面
      this.pageTagInfo.installationPackageId = item.id
      this.on_off_configuration()
      if (item.pageTagInfoId !== null) {
        this.uploadLoading = true
        console.log(item.pageTagInfoId)
        this.$axios.post('/pageTag/getPageTagById', {id: item.pageTagInfoId})
          .then(resp => {
            let {
              data
            } = resp
            if (data.code === 0) {
              this.pageTagInfo.img = data.data.img
              this.pageTagInfo.code = data.data.code
              this.pageTagInfo.title = data.data.title
              this.pageTagInfo.url = data.data.url
              this.pageTagInfo.description = data.data.description
              this.pageTagInfo.information = data.data.information
              this.pageTagInfo.sort = data.data.sort
            } else {
              this.showErrorMessage(data.msg)
              this.closePageTagInfo()
            }
          })
          .catch(err => {
            this.showErrorMessage(err)
            this.closePageTagInfo()
          })
          .finally(() => {
            this.uploadLoading = false
          })
      }
    },
    submitPageTagInfo () {
      // 提交页面标签设置
      this.uploadLoading = true
      this.$axios.post('/pageTag/setPageTagInfo', this.pageTagInfo)
        .then(resp => {
          let {
            data
          } = resp
          if (data.code === 0) {
            this.closePageTagInfo()
          } else {
            this.showErrorMessage(data.msg)
          }
        })
        .catch(err => {
          this.showErrorMessage(err)
        })
        .finally(() => {
          this.uploadLoading = false
        })
    },
    closePageTagInfo () {
      // 关闭页面标签设置界面
      this.pageTagInfo.installationPackageId = null
      this.pageTagInfo.img = null
      this.pageTagInfo.code = null
      this.pageTagInfo.title = null
      this.pageTagInfo.url = null
      this.pageTagInfo.description = null
      this.pageTagInfo.information = null
      this.pageTagInfo.sort = null
      this.on_off_configuration()
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

.navList .upload-box {
  width: 80px;
  height: 30px;
  background-color: rgb(204,204,204);
  color: rgb(94,94,94);
  border: 0px;
  font-size: 14px;
  cursor: pointer;
  position: absolute;
  font-weight: bold;
  right: 50px;
  margin-top: 10px;
  padding-top: 0px;
}

.navList .upload-box:hover {
  background-color: rgb(0, 110, 255);
  color: #fff;
}

.upload-box label {
  cursor: pointer;
  width: 100%;
  height: 100%;
  position: relative;
  display: flex;
  align-items: center;
  background-color: transparent;
  justify-content: center;
}

.uploadLoading .uploadLoading-mask{
  background-color: #000;
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.upload-box input {
  display: none;
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
  height: 650px;
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

.configuration .formitem,.configuration .formitem-img{
  height: 40px;
  margin-top: 28px;
  margin-left: 20px;
  display: flex;
}

.configuration .formitem-img{
  margin-bottom: 90px;
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

.inputBox .formitem-select{
  position: absolute;
  top: 42px;
  height: 150px;
  overflow: auto;/* 处理溢出 */
}

.inputBox .formitem-select::-webkit-scrollbar {
  height: 0px;
  width: 0px;
}

.inputBox .formitem-select.hidden{
  display: none;
}

.formitem-select .select{
  width: 260px;
  height: 20px;
  padding: 10px 20px;
  background-color: rgb(242,242,242);
  overflow: hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
}

.formitem-select .select:hover{
  background-color: #4499ff;
  color: #fff;
}

.formitem input{
  width: 280px;
  height: 25px;
  position: relative;
  left: 10px;
  background-color: transparent;
  border:none;
  outline:none;
  font-size: 12px;
}

.formitem-img .img-title{
  margin-top: 40px;
}

.formitem-img .updateImgBox{
  border-radius: 4px;
  border: 1px dashed rgb(217,217,217);
  width: 104px;
  height: 104px;
  background-color: rgb(250,250,250);
  position: relative;
  margin-left: 70px;
}

.formitem-img .updateImgBox.active{
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

.updateImgBox .uploadImg{
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

.formitem-img .reminder{
  color: rgba(153,153,153,0.847);
  font-size: 10px;
  position: relative;
  margin-top: 40px;
  margin-left: 20px;
}

.configuration .button-close{
  position: absolute;
  left:398px;
  top:604px;
}

.configuration .button-verify{
  position: absolute;
  left:305px;
  top:604px;
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
