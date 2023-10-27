<template>
  <div>
      <el-form label-position="right" align="left" size="small" :inline="true"> 
        <el-form-item label="备注：">
          <el-input v-model="remark" style="width:200px;" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <template>
            <input type="file" id="fileExport" @change="handleFileChange" ref="inputer"> 
          </template>
        </el-form-item>         
        <el-form-item>
          <template>
            <audio id="myAudio" controls height="100" width="100">
              <source src="/service-wav/wav/getFile" type="audio/wav">
            </audio>            
          </template>
        </el-form-item>            
      </el-form>
      <div>{{fileInfo}} 文件大小：{{fileSize}}</div>
      <el-form label-position="right" align="left" size="small" :inline="true">   
        <el-form-item>
          <template>
            <el-button type="primary" @click="getFileInfo">获取文件信息</el-button>
            <el-button type="primary" @click="uploadApp('/service-wav/wav/upload')">锐捷语音识别</el-button>
            <el-button type="primary" @click="uploadApp('/service-wav/wav/uploadBaidu')">百度语音识别</el-button>  
          </template>
        </el-form-item>                                     
      </el-form>
      <el-form label-position="right" align="left" size="small" :inline="true">   
        <el-form-item>
          <template>
            <el-button type="primary" @click="getSoundUp">下载增益文件</el-button>
            <a target="_blank" href="/service-wav/wav/soundUp">获取增益文件2</a>
          </template>
        </el-form-item>                                     
      </el-form>      
      <el-form label-position="right" align="left" size="small" :inline="true">
        <el-form-item label="文本内容：" >
          <el-input v-model="textContent"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dealText">百度词法识别</el-button>
          <el-button type="primary" @click="uploadApp('/service-wav/wav/uploadBaidu2')">百度语音+词法识别</el-button>
        </el-form-item>
      </el-form>  
      <div>
        <el-button type="primary" @click="clear()">清空消息</el-button>
      </div>      
      <!-- <div>
        上传速率：{{uploadRate}} B/s
      </div>      
      <el-progress v-show="uploadPercent>0&&uploadPercent<99" :percentage="uploadPercent"></el-progress> -->
      <div style="white-space:pre-line">
        <span v-html="resultInfo"></span>
        <span v-html="resultTextInfo"></span>
      </div>
  </div>
</template>

<script>
export default {
  name: 'UploadAppFile',
  props: {
    //msg: String
  },
  data() {
    return {
      file:null,
      formData:null,
      remark:"",
      fileSize:0,
      uploadPercent:0,
      uploadTime:0,
      uploadRate:0,
      fileInfo:"请选择文件", 
      resultInfo:"",   
      textContent:"第一组加1分",  
      resultTextInfo:"",  
    }
  },   
  mounted() {

  }, 
  methods: {
    handleFileChange (e) {
      console.log(e);
      let inputDOM=this.$refs.inputer;
      this.file=inputDOM.files[0];
      this.fileSize = this.file.size;
      this.getFileInfo();
    },  
    clear(){
      this.resultInfo = "";
      this.resultTextInfo = "";
    },
    getFileInfo(){
      this.formData=new FormData();
      this.formData.append("file",this.file);
      this.formData.append("remark",this.remark); 
      let d = new Date();
      this.uploadTime = d.getTime();              
      //var vm = this;
      this.$axios({
        url:"/service-wav/wav/getFileInfo",
        data:this.formData, 
        method: "post",
        headers:{
          'Content-Type':'multipart/form-data'
        }
      }).then(res => {
        console.log(res);
        if(res.status==200){
          this.$message.success('成功');
          this.fileInfo = JSON.stringify(res.data);
          var myAudio = document.getElementById("myAudio");
          myAudio.load();          
        }else{
          this.$message.error('失败');
        }
      }).catch(error => { 
        console.log(error) 
      })      
    },  
    getSoundUp(){
      this.formData=new FormData();
      this.formData.append("file",this.file);
      this.formData.append("remark",this.remark); 
      let d = new Date();
      this.uploadTime = d.getTime();              
      //var vm = this;
      this.$axios({
        url:"/service-wav/wav/soundUp",
        data:this.formData, 
        method: "get",
        headers:{
          'Content-Type':'multipart/form-data'
        }
      }).then(res => {
        console.log(res);
        if(res.status==200){
          this.$message.success('成功');   
        }else{
          this.$message.error('失败');
        }
      }).catch(error => { 
        console.log(error) 
      })      
    },    
    uploadApp(url){
      if(this.file == null){
        this.$message.error('请选择文件');
        return;
      }
      this.formData=new FormData();
      this.formData.append("file",this.file);
      this.formData.append("remark",this.remark); 
      let d = new Date();
      this.uploadTime = d.getTime();              
      var vm = this;
      this.$axios({
        url:url,
        data:this.formData, 
        method: "post",
        headers:{
          'Content-Type':'multipart/form-data'
        },
        onUploadProgress(progressEvent){
          console.log(progressEvent);
          if (progressEvent.lengthComputable) {
            var percent =  Math.ceil(100*progressEvent.loaded/progressEvent.total);
            vm.uploadPercent = percent;
            let now = new Date();
            vm.uploadRate = Math.ceil(1000*progressEvent.loaded/(now.getTime()-vm.uploadTime));            
          }
        }
      }).then(res => {
        console.log(res);
        if(res.status==200){
          this.$message.success('成功');
          this.resultInfo += JSON.stringify(res.data)+"<br/>";
        }else{
          this.$message.error('失败');
        }
      }).catch(error => { 
        console.log(error) 
      })
    },
    dealText(){
      this.$axios({
        url:"/service-wav/text/dealText",
        data:this.textContent, 
        method: "post",
        headers:{
          'Content-Type':'application/json'
        },        
      }).then(res => {
        if(res.status==200){
          this.$message.success('成功');
          this.resultTextInfo = JSON.stringify(res.data);
        }else{
          this.$message.error('失败');
        }
      }).catch(error => { 
        console.log(error) 
      })      
    }, 
  }    
}
</script>

<style scoped>

</style>
