<template>
    <div class="camera_outer">
      <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
      <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>

      <div v-if="imgSrc" class="img_bg_camera">
        <img :src="imgSrc" alt="" class="tx_img">
      </div>
      <button @click="getCompetence()">打开摄像头</button>
      <button @click="stopNavigator()">关闭摄像头</button>
      <button @click="setImage()">拍照</button>
    </div>
</template>
<script>
export default {
  data () {
    return {
      videoWidth: 1960,
      videoHeight: 1080,
      imgSrc: '',
      thisCancas: null,
      thisContext: null,
      thisVideo: null
    }
  },
  methods: {
    getCompetence () {
      var _this = this
      this.thisCancas = document.getElementById('canvasCamera')
      this.thisContext = this.thisCancas.getContext('2d')
      this.thisVideo = document.getElementById('videoCamera')
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {}
      }
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = function (constraints) {
          var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
          if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
          }
          return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject)
          })
        }
      }
      var constraints = { audio: false, video: { width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)' } }
      navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
        if ('srcObject' in _this.thisVideo) {
          _this.thisVideo.srcObject = stream
        } else {
          _this.thisVideo.src = window.URL.createObjectURL(stream)
        }
        _this.thisVideo.onloadedmetadata = function (e) {
          _this.thisVideo.play()
        }
      }).catch(err => {
        console.log(err)
      })
    },
    setImage () {
      var _this = this
      _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
      var image = this.thisCancas.toDataURL('image/png')
      console.log(image)
      _this.imgSrc = image
      this.$emit('refreshDataList', this.imgSrc)
    },
    dataURLtoFile (dataurl, filename) {
      var arr = dataurl.split(',')
      var mime = arr[0].match(/:(.*?);/)[1]
      var bstr = atob(arr[1])
      var n = bstr.length
      var u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], filename, { type: mime })
    },
    stopNavigator () {
      this.thisVideo.srcObject.getTracks()[0].stop()
    }
  }
}
</script>

<style scoped>
.camera_outer{
  position: relative;
  overflow: hidden;
  background: red no-repeat center;
  background-size: 100%;
}
video,canvas,.tx_img{
  -moz-transform:scaleX(-1);
  -webkit-transform:scaleX(-1);
  -o-transform:scaleX(-1);
  transform:scaleX(-1);
}
.btn_camera{
  position: absolute;
  bottom: 4px;
  left: 0;
  right: 0;
  height: 50px;
  background-color: rgba(0,0,0,0.3);
  line-height: 50px;
  text-align: center;
  color: #ffffff;
}
.bg_r_img{
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
.img_bg_camera{
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
}
img{
  width: 80%;
  height: 80%;
}
.img_btn_camera{
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 50px;
  line-height: 50px;
  text-align: center;
  background-color: rgba(0,0,0,0.3);
  color: #ffffff;
}
.loding_img{
  width: 50px;
  height: 50px;
}
</style>
