webpackJsonp([3],{W0r5:function(s,t){},"vtQ+":function(s,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a={components:{errorMessage:e("V7I0").a},name:"Contacts",data:function(){return{contactTag:{code:0,msg:"成功!",total:3,list:[{groupName:"默认分组",list:[{userId:"1",userName:"北京客户您好，我想咨询一下资费问题",userImg:""},{userId:"2",userName:"上海客户",userImg:""}]},{groupName:"朋友",list:[{userId:"1",userName:"xxx",userImg:""}]}]},userInfo:{userId:"16455",userImg:"",userName:"北京客户",userSign:"北京客户户北京客户北京客户北京客户",userPhone:"19855143351",userAddress:"山西 太原",userDepartment:"小区管控"}}},methods:{activeLink:function(s){document.querySelectorAll(".contactBox.active").forEach(function(s){return s.classList.remove("active")}),s.currentTarget.className+=" active"},sendMessage:function(s){},showErrorMessage:function(s){this.$refs.errorMessage.setErrorMessage(s)}}},r={render:function(){var s=this,t=s.$createElement,e=s._self._c||t;return e("div",{staticClass:"contacts"},[e("div",{staticClass:"contactsList"},[s._m(0),s._v(" "),e("div",{staticClass:"contactTag"},s._l(s.contactTag.list,function(t){return e("div",{key:t.groupName},[e("div",{staticClass:"groupName"},[s._v(s._s(t.groupName))]),s._v(" "),s._l(t.list,function(t){return e("div",{key:t.userId},[e("div",{staticClass:"contactBox",on:{click:function(t){return s.activeLink(t)}}},[e("div",{staticClass:"contactCircle"},[e("img",{staticClass:"img",attrs:{src:t.userImg}})]),s._v(" "),e("label",{staticClass:"userName"},[s._v(s._s(t.userName))])])])})],2)}),0)]),s._v(" "),e("div",{staticClass:"information"},["{}"!==JSON.stringify(s.userInfo)?e("div",{staticClass:"information-box"},[e("div",{staticClass:"user-information"},[e("div",{staticClass:"nameAndImg-box"},[e("div",[e("div",{staticClass:"userName"},[s._v(s._s(s.userInfo.userName))]),s._v(" "),e("div",{staticClass:"userSign"},[s._v(s._s(s.userInfo.userSign))])]),s._v(" "),e("img",{staticClass:"userImg",attrs:{src:s.userInfo.userImg}})]),s._v(" "),e("div",{staticClass:"line"}),s._v(" "),e("div",{staticClass:"otherInfo-box"},[e("div",{staticClass:"userPhone"},[e("label",[s._v("电话号码:")]),s._v(s._s(s.userInfo.userPhone))]),s._v(" "),e("div",{staticClass:"userAddres"},[e("label",[s._v("地  区:")]),s._v(s._s(s.userInfo.userAddress))]),s._v(" "),e("div",{staticClass:"userAddres"},[e("label",[s._v("部  门:")]),s._v(s._s(s.userInfo.userDepartment))])]),s._v(" "),e("button",{staticClass:"button",attrs:{type:"submit"},on:{click:function(t){return s.sendMessage(s.userInfo.userId)}}},[s._v("发消息")])])]):s._e()]),s._v(" "),e("error-message",{ref:"errorMessage"})],1)},staticRenderFns:[function(){var s=this.$createElement,t=this._self._c||s;return t("div",{staticClass:"search"},[t("div",{staticClass:"search-iconfont"},[t("span",{staticClass:"iconfont",attrs:{onclick:"document.querySelector('.search').classList.toggle('active')"}},[this._v("")])]),this._v(" "),t("div",{staticClass:"search-input"},[t("input",{attrs:{type:"text",placeholder:"Search",id:"search-input"}})]),this._v(" "),t("div",{staticClass:"clear-iconfont"},[t("span",{staticClass:"iconfont",attrs:{onclick:"document.getElementById('search-input').value = ''"}},[this._v("")])])])}]};var i=e("VU/8")(a,r,!1,function(s){e("W0r5")},"data-v-56d28279",null);t.default=i.exports}});
//# sourceMappingURL=3.5ba05cb41d6caa20a307.js.map