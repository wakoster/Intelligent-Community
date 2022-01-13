<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
  *{
    margin: 0;
    padding: 0;
  }
  html{
    background-color: rgba(0,110,255,0.098);
    font-family: PingFangSC-Regular, "PingFang SC";
    font-size: 14px;
  }
</style>
