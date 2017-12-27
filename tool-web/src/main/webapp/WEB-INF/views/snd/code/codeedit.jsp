<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/snd/common/common_title.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> home </a></li>
            <li>接口管理</a></li>
            <li class="active">测试接口</li>
        </ol>
    </section>

    <div id="app">
		<el-row>
		  <el-col :span="1"> &nbsp;&nbsp;&nbsp; </el-col>
		  <el-col :span="9">
		  <el-card class="box-card" style="margin:6% auto">
    		<el-form :model="codeForm"  label-width="100px">
    			 <el-form-item label="表名">
				    <el-input :disabled="true" v-model="codeForm.tableName"></el-input>
				  </el-form-item>
				  <el-form-item label="接口标识">
				    <el-input :disabled="true"  v-model="codeForm.id"></el-input>
				  </el-form-item>
				  <el-form-item label="接口类型">
			  		 <el-select :disabled="true" v-model="codeForm.codeType"    placeholder="请选择接口类型">
   						<el-option v-for="item in codeTypeSelect"  :key="item.code" :label="item.name"  :value="item.code"></el-option>
					 </el-select>
				  </el-form-item>
				  <el-form-item label="接口名称">
				    <el-input  v-model="codeForm.codeName"></el-input>
				  </el-form-item>

				  <el-form-item label="接口参数" >
				    <el-input type="textarea" :rows=12 v-model="codeForm.sqlSen"></el-input>
				  </el-form-item>
				  <el-form-item>
				  	<el-button type="success" @click="editCodeFn()">修改</el-button>
				    <el-button type="primary" @click="testCodeFn()">立即测试</el-button>
				  </el-form-item>		    	      
			</el-form>		
			</el-card>  
		  </el-col>
		  <el-col :span="2"> &nbsp;&nbsp;&nbsp; </el-col>
		  <el-col :span="11">
		    <el-card class="box-card" style="margin:5% auto">
	    		<h3>接口返回结果：</h3>
				<el-input type="textarea" :max-rows=34  id="json_result">
				
				</el-input>
			</el-card>
		  </el-col>
		  <el-col :span="1"> &nbsp;&nbsp;&nbsp; </el-col>
		</el-row>
    <!-- /.content -->
</div>

<%@include file="/WEB-INF/views/snd/common/common_bottom_plugin.jsp" %>
<link rel="stylesheet" href="/static/src/plugins/jsonview/jquery.jsonview.min.css" />
<script type="text/javascript" src="/static/src/plugins/jsonview/jquery.jsonview.js"></script>
  
 <script>
  	 var object = <%= request.getAttribute("obejct") %>;
	  new Vue({
	      el: '#app',
	      data: function(){
	        return { 
	        	codeTypeSelect:object.d_code_type,
	        	codeForm:object.code,
	        	codeHost:object.code.host,
	        	editForm:{
	        		id:object.code.id,
	        		codeName:'',
	        		sqlSen:''
	        	}
	        }
	      },
	      methods: {
	    	  testCodeFn(){
	    		  var a = this;
	    		  var param = a.codeForm.sqlSen;
	    		  var obj = eval('(' + param + ')');
	    		  var url = a.codeHost+"/znph/code/excute";
	    		  
              	  axios.post(url,obj).then(function(response){
              		 $('#json_result').JSONView(response.data,{nl2br: true});
              		 if(response.data.code == "0"){
              			a.editCodeFn();
              		 }
            	  })
	    	  },
	    	  editCodeFn(){
	    		  var a = this;
	    		  a.editForm.sqlSen = a.codeForm.sqlSen;
	    		  a.editForm.codeName = a.codeForm.codeName;
              	  axios.post("/sys/code",a.editForm).then(function(response){
              		 if(response.data.code == "0"){
              	        a.$message({
              	          message: '操作成功!',
              	          type: 'success'
              	        });
              		 }else{
               	        a.$message({
                	          message: response.data.message,
                	          type: 'error'
                	   });
              		 }
            	  })
	    	  }

	      }
	    })
  
  </script>




<%@include file="/WEB-INF/views/snd/common/common_bottom.jsp" %>
