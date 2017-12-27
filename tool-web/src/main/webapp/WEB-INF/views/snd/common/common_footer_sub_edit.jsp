        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <script src="/static/src/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="/static/src/bootstrap/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="/static/src/dist/js/app.min.js"></script>
	<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <script src="/static/src/js/utils/BootstrapMenu.min.js"></script>
    <script src="/static/src/js/utils/fileinput.min.js"></script>
    <script src="/static/src/js/utils/validate.js"></script>
    <script src="/static/src/js/utils/zh.js"></script>
    <script src="/static/src/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/src/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script src="/static/src/plugins/iCheck/icheck.min.js"> </script>
    <script  type="text/javascript" src="/static/src/plugins/wangeditor/wangEditor.min.js"> </script>
  	<!-- 先引入 Vue -->
   <script type="text/javascript">
	  var E = window.wangEditor;
	  var editor = new E('#editorElem');
	  // 自定义菜单配置
	  editor.customConfig.menus = [
			    'head',  // 标题
			    'bold',  // 粗体
			    'italic',  // 斜体
			    'underline',  // 下划线
			    'strikeThrough',  // 删除线
			    'foreColor',  // 文字颜色
			    'backColor',  // 背景颜色
			    'link',  // 插入链接
			    'justify',  // 对齐方式
			    'quote',  // 引用
			    'image',  // 插入图片
			    'video',  // 插入视频
			    'code',  // 插入代码
			    'undo',  // 撤销
			    'redo'  // 恢復
	  ]

	</script> 
	 <!-- 先引入 Vue -->
	 <script src="/static/src/element/js/vue.js"></script>
	 <script src="/static/src/element/js/index.js"></script>
</html>