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
      <!-- 弹出表单 开始 -->
	  <el-dialog title="新增表" :visible.sync="dialogFormVisible">
		  <el-form :model="tableForm">
		    <el-form-item label="表名" >
		      <el-input v-model="tableForm.tableName" ></el-input>
		    </el-form-item>
		    <el-form-item label="表说明" >
		      <el-input v-model="tableForm.tableRemark"></el-input>
		    </el-form-item>
			  <el-form-item label="表语句">
			    <el-input type="textarea" :rows=8 v-model="tableForm.tableSen"></el-input>
			  </el-form-item>
			  <el-form-item label="域名">
		  		 <el-select v-model="tableForm.host" clearable placeholder="请选择域名">
	 						<el-option v-for="item in hostSelect"  :key="item.code" :label="item.name"  :value="item.code"></el-option>
				 </el-select>
			  </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="addFn()">确 定</el-button>
		  </div>
	  </el-dialog>
      <!-- 弹出表单 结束 -->
      
      <el-dialog title="提示" :visible.sync="centerDialogVisible" width="30%" center>
	      <span>{{ msg }}</span>
		  <span slot="footer" class="dialog-footer">
		    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
		  </span>
	  </el-dialog>
    
    
    
    
           <div  style="margin: 40px 30px 10px 10px;min-height: 60px;" >
	           <el-form :inline="true" :model="search" class="demo-form-inline">
				  <el-form-item>
				    	<el-input v-model="search.tableName" placeholder="请输入表名称" ></el-input>
				  </el-form-item>
				  <el-form-item label="域名">
				  		 <el-select v-model="search.host" clearable placeholder="全部">
	   						<el-option v-for="item in hostSelect"  :key="item.code" :label="item.name"  :value="item.code"></el-option>
						 </el-select>
				  </el-form-item>		
				  <el-form-item>
				    <el-button type="primary" @click="searchfn">搜索</el-button>
				  </el-form-item>
				  <el-form-item>
				    <el-button type="primary" @click="dialogFormVisible = true">新增表</el-button>
				  </el-form-item>
				</el-form>
          </div>
         <div style="margin: 10px">
          		  <el-table :data="list"  border  >
				    	<el-table-column   prop="id" label="标识" min-width="150"></el-table-column>
				    	<el-table-column   prop="tableName" label="表名" min-width="150"></el-table-column>
				    	<el-table-column   prop="tableRemark" label="表说明" min-width="200"></el-table-column>
				    	<el-table-column   prop="tableSen" label="表语句" min-width="400"></el-table-column>
				    	<el-table-column   prop="number" label="接口数量" min-width="80"></el-table-column>
				    	<el-table-column  prop="createTime"  label="创建时间" min-width="150"></el-table-column>		
				    	 <el-table-column label="操作" min-width="200">
						      <template scope="scope">
						        <el-button  type="success" @click="viewCodeListFn(scope.row)">接口列表</el-button>	
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
	  new Vue({
	      el: '#app',
	      data: function(){
	        return { 	   
	        	centerDialogVisible:false,
	        	msg:'',
	        	dialogFormVisible:false,
	        	hostSelect:object.d_host,
	        	tableForm:{
	        		tableName:'',
	        		tableSen:'',
	        		tableRemark:'',
	        		host:''
	        	},
			    search:{
			    	 tableName:'',
			    	 host:'',
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
	    	 hostExchange(code){	   
	    		  var dictArray = object.d_host;
	    		  return getDictName(dictArray,code);
	    	  }
	      },
	      methods: {
	    	  searchfn(){
	    		  var a = this;  	
	    		  a.search.page =a.currentPage;
              	  axios.post("/sys/table/search",a.search).then(function(response){
						a.list = response.data.list;
						a.totalPage = response.data.data.total;
              	  })
	    	  },
	    	  addFn(row) {
	    		  var a = this;  	
              	  axios.post("/znph/code",a.tableForm).then(function(response){
              		  if(response.data.code == "0"){
              			  a.dialogFormVisible = false;
        	    		  a.search.page =a.currentPage;
                      	  axios.post("/sys/table/search",a.search).then(function(response){
        						a.list = response.data.list;
        						a.totalPage = response.data.total;
        						//清空表单，更新数据
        						a.tableForm.tableName='';
        						a.tableForm.tableSen='';
        						a.tableForm.tableRemark='';
        						a.tableForm.host='';
                      	  })
              		  }else{
              	 		  a.centerDialogVisible=true,
        	    		  a.msg = response.data.message
              		  }
              	  })

	    	  },
	    	  viewCodeListFn(row) {
	    		  window.location.href = "/sys/code/list?tableId="+row.id;
	    	  },
	    	  closeCenterDialogFn(){
	    		  this.centerDialogVisible=true,
	    		  this.msg = ''
	    	  }

	      }
	    })
  
  </script>




<%@include file="/WEB-INF/views/snd/common/common_bottom.jsp" %>
