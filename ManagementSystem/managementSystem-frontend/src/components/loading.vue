<template>
  <div class="loading">
    <div class="loader-inner ball-beat">
      <div></div>
      <div></div>
      <div></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Loading'
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="scoped">
.loading {
  top: 50%;
  left: 50%;
  position: fixed;
  -webkit-transform: translate(-50%,-50%);
  transform: translate(-50%,-50%);
}

@-webkit-keyframes ball-beat{
  50%{
    opacity: 0.2;
    -webkit-transform: scale(0.75);
    transform: scale(0.75);
  }
  100%{
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

@keyframes ball-beat{
  50%{
    opacity: 0.2;
    -webkit-transform: scale(0.75);
    transform: scale(0.75);
  }
  100%{
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

.ball-beat > div{
  background-color: #279fcf;
  width: 10px;
  height: 10px;
  border-radius: 100% !important;
  margin: 2px;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
  display: inline-block;
  -webkit-animation: ball-beat 0.7s 0s infinite linear;
  animation: ball-beat 0.7s 0s infinite linear;
}

.ball-beat > div:nth-child(2n-1){
  -webkit-animation-delay: 0.35s !important;
  animation-delay: 0.35s !important;
}
</style>
