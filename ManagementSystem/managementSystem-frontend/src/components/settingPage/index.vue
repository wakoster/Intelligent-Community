<template>
  <div class="settingPage">
    <div class="topbox"></div>
    <div class="pageBox">
      <div class="home">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'settingPage'
}
</script>

<style scoped="scoped">
.settingPage{
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.settingPage .topbox{
  width: calc(100% - 80px);
  height: 46px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #fff;
  padding-left: 40px;
  padding-right: 40px;
  padding-top: 5px;
  padding-bottom: 5px;
  box-shadow: 0 2px 6px rgb(100 100 100 / 15%);
}

.settingPage .topbox.hidden{
  display: none;
}

.settingPage .pageBox{
  position: absolute;
  width: 100%;
  height: calc(100% - 56px);
  overflow: auto;
  overflow-x: hidden;
}

.settingPage .pageBox::-webkit-scrollbar {
  width: 5px;
}

.settingPage .pageBox::-webkit-scrollbar-thumb {
  background: #8d8d8d;
  border-radius: 10px;
}

.settingPage .pageBox::-webkit-scrollbar-track-piece {
  background: transparent;
}

.pageBox .home{
  position: relative;
  margin: 0 auto;
  width: 85%;
  min-width: 950px;
  min-height: 500px;
  background-color: #fff;
  padding: 30px;
  margin-top: 50px;
  margin-bottom: 50px;
  box-shadow: 0 2px 6px rgb(100 100 100 / 30%);
}
</style>
