<template>
  <div class="errorMessage" id="errorMessage">
    <label>⚠{{errorMessage}}</label>
  </div>
</template>

<script>
export default {
  name: 'ErrorMessage',
  data: function () {
    return {
      errorMessage: null
    }
  },
  methods: {
    setErrorMessage: function (errorMessage) {
      this.errorMessage = errorMessage
      var el = document.getElementById('errorMessage')
      setTimeout(() => {
        el.style.transform = 'translate(-50%,calc(10px + 100%))'
        el.style.transition = 'all 1s ease'
      }, 0)
      setTimeout(() => {
        el.style.transform = 'translate(-50%,calc(0px - 100%))'
        el.style.transition = 'all 1s ease'
      }, 2000)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="scoped">
.errorMessage{
  position: fixed;
  left: 50%;
  top: -5px;
  transform: translate(-50%,-100%);
  padding: 10px 20px;
  border-radius: 4px;
  background-color: rgb(250,250,250);
  border: 1px solid rgb(0,110,255);
  font-size: 14px;
  font-weight: 650;
  color: rgba(255, 0, 0, 0.8);
  display: flex;
  align-items: center;
}
</style>
