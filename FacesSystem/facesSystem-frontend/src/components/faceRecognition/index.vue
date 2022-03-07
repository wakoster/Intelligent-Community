<template>
  <div class="faceRecognition">
    <!-- <div id="contentHolder" v-if="active==2">
      <video id="video" width="320" height="320" autoplay ref="videos"></video>
    </div> -->
  </div>
</template>

<script>
export default {
  name: 'faceRecognition'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="scoped">
.faceRecognition video{
  width: 1000px;
  height: 1000px;
}
</style>
