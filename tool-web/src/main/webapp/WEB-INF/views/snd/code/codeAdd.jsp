<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/snd/common/common_title.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper">

	 <div id="app">
	    <!-- Content Header (Page header) -->
	    <section>
	        <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> home</a></li>
	            <li>接口管理</a></li>
	            <li class="active">新增接口</li>
	        </ol>
	    </section>

    	
    	<div style="margin: 3% auto;width:70%;height: 90%">
    		<el-card class="box-card" style="width: 90%">
    		<h4>表名:&nbsp;&nbsp; <span style="color: red;">{{ tableName }} </span></h3>
    		<br>
    		<el-form :model="codeForm" :rules="codeRule" ref="codeRule" label-width="100px">
				  <el-form-item label="接口名称">
				    <el-input v-model="codeForm.codeName"></el-input>
				  </el-form-item>
				  <el-form-item label="接口类型">
			  		 <el-select v-model="codeForm.codeType" clearable placeholder="请选择接口类型">
   						<el-option v-for="item in codeTypeSelect"  :key="item.code" :label="item.name"  :value="item.code"></el-option>
					 </el-select>
				  </el-form-item>
				  <el-form-item label="参数">
				    <el-input type="textarea" :rows=8 v-model="codeForm.sqlSen"></el-input>
				  </el-form-item>
				  <el-form-item>
				    <el-button type="primary" @click="addCodeFn()"> 立即添加 </el-button>
				  </el-form-item>		    	      
			</el-form>
			</el-card>
    	</div>
    	
    </div>
    <!-- /.content -->
</div>
<%@include file="/WEB-INF/views/snd/common/common_bottom_plugin.jsp" %>

 <script>
  
  	 var object = <%= request.getAttribute("object") %>;
	  new Vue({
	      el: '#app',
	      data: function(){
	        return { 	
	        	codeTypeSelect:object.d_code_type,
	        	tableName:object.tableName,
	        	codeForm:{
	        		codeName:'',
	        		sqlSen:'',
	        		codeType:'',
	        		tableId:object.tableId
	        	},
	        	codeRule:{
	        		 codeName: [
	                    { required: true, message: '请填写接口名称', trigger: 'change' }
	                  ],
	        		 codeType: [
		                  { required: true, message: '请选择接口类型', trigger: 'change' }
		             ],
	            }
	        }
	      },
	      methods: {
	    	  addCodeFn(){
	    		  var a = this;
              	  axios.post("/sys/code",a.codeForm).then(function(response){
						if(response.data.code == "0"){
							window.location.href = "/sys/code/list?tableId="+object.tableId;
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
