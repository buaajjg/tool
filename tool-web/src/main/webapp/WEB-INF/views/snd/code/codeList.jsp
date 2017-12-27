<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/snd/common/common_title.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> home </a></li>
            <li>接口管理</a></li>
            <li class="active">接口列表</li>
        </ol>
    </section>

    <!-- Main content -->
    <div id="app">
           <div  style="margin: 40px 30px 10px 10px;min-height: 60px;" >
	           <el-form :inline="true" :model="search" class="demo-form-inline">

				  <el-form-item>
				    	<el-input v-model="search.codeName" placeholder="请输入名称" ></el-input>
				  </el-form-item>
				  <el-form-item label="接口状态">
				  		 <el-select v-model="search.status" clearable placeholder="全部">
	   						<el-option v-for="item in statusSelect"  :key="item.code" :label="item.name"  :value="item.code"></el-option>
						 </el-select>
				  </el-form-item>
				  <el-form-item>
				    <el-button type="primary" @click="searchfn">搜索</el-button>
				  </el-form-item>
				  <el-form-item>
				    <el-button type="primary" @click="addFn">新增</el-button>
				  </el-form-item>
				</el-form>
          </div>
         <div style="margin: 10px">
          		  <el-table :data="list"  border  >
				    	<el-table-column   prop="id" label="标识" min-width="150"></el-table-column>
				    	<el-table-column   prop="codeName" label="名称" min-width="200"></el-table-column>
				    	<el-table-column   prop="status" label="接口状态" min-width="100">
				    		<template scope="scope">
						        <span style="margin-left: 10px">{{ scope.row.status | statusExchange }}</span>
						     </template>		
				    	</el-table-column>
				    	<el-table-column  prop="updateTime"  label="修改时间" min-width="180"></el-table-column>		
				    	 <el-table-column label="操作" min-width="200">
						      <template scope="scope">
						        <el-button  type="success" @click="statusToggle(scope.row)">{{ scope.row.status == '1'?'禁用':'启用' }}</el-button>	
						        <el-button v-if="scope.row.status == 1"   type="info" @click="testFn(scope.row)">编辑/测试</el-button>	
						      </template>
					    </el-table-column>		    					    	
				  </el-table>
				  <br>
			    <div style="  float: right;margin-right: 10%;">
		  			<el-pagination  layout="prev, pager, next" :page-size="pagesize" :current-page.sync="currentPage" @current-change="searchfn" :total="totalPage" > </el-pagination>				  
		 		</div>
          </div>
    </div>
    <!-- /.content -->
</div>
<%@include file="/WEB-INF/views/snd/common/common_bottom_plugin.jsp" %>

 <script>
  
  var object = <%= request.getAttribute("object") %>;
  var table = <%= request.getAttribute("table") %>;
	  new Vue({
	      el: '#app',
	      data: function(){
	        return { 	   
	        	statusSelect:object.d_have,
			    search:{
			    	 tableId:table.tableId,
			    	 codeName:'',
			    	 status:'',
			    	 size:'',
			    	 page:''
			     },
			     pagesize:10,
			     currentPage:1,
			     totalPage:object.total,
			     list:object.list			     
	        }
	      },
	      filters:{    	  
	    	  statusExchange(code){	   
	    		  var dictArray = object.d_have;
	    		  return getDictName(dictArray,code);
	    	  }
	      },
	      methods: {
	    	  searchfn(){
	    		  var a = this;  	
	    		  a.search.page =a.currentPage;
              	  axios.post("/sys/code/search",a.search).then(function(response){
						a.list = response.data.data.list;
						a.totalPage = response.data.data.total;
              	  })
	    	  },
	    	  testFn(row) {
	    		  window.location.href = "/sys/code/test?id="+row.id;
	    	  },
	    	  addFn(row) {
	    		  window.location.href = "/sys/code/add?tableId="+table.tableId;
	    	  },
	    	  statusToggle(row){
	    		  var status = row.status == "1"?"2":"1";
	    		  url = "/sys/code/toggle/"+row.id+"/"+status;
              	  axios.get(url).then(function(response){
              		  row.status = status;
            	  })
	    	  }
	      }
	    })
  
  </script>




<%@include file="/WEB-INF/views/snd/common/common_bottom.jsp" %>
