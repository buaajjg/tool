<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="javascript:void(0)" class="logo">
            <span class="logo-mini"></span>
        	<span class="logo-lg"><b>接口工具平台</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->
                    <%--<li class="dropdown messages-menu">--%>
                        <%--<!-- Menu toggle button -->--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                            <%--<i class="fa fa-envelope-o"></i>--%>
                            <%--<span class="label label-success">4</span>--%>
                        <%--</a>--%>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li class="header">You have 4 messages</li>--%>
                            <%--<li>--%>
                                <%--<!-- inner menu: contains the messages -->--%>
                                <%--<ul class="menu">--%>
                                    <%--<li><!-- start message -->--%>
                                        <%--<a href="#">--%>
                                            <%--<div class="pull-left">--%>
                                                <%--<!-- User Image -->--%>
                                                <%--<img src="/static/src/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">--%>
                                            <%--</div>--%>
                                            <%--<!-- Message title and timestamp -->--%>
                                            <%--<h4>--%>
                                                <%--Support Team--%>
                                                <%--<small><i class="fa fa-clock-o"></i> 5 mins</small>--%>
                                            <%--</h4>--%>
                                            <%--<!-- The message -->--%>
                                            <%--<p>Why not buy a new awesome theme?</p>--%>
                                        <%--</a>--%>
                                    <%--</li>--%>
                                    <%--<!-- end message -->--%>
                                <%--</ul>--%>
                                <%--<!-- /.menu -->--%>
                            <%--</li>--%>
                            <%--<li class="footer"><a href="#">See All Messages</a></li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<!-- /.messages-menu -->--%>

                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                  		<a href="javascript:void(0)" class="dropdown-toggle" >
                            <!-- The user image in the navbar-->
                            <img src="/static/src/dist/img/user5-128x128.jpg" class="user-image" alt="头像">
                        </a>
                    </li>
                   <!-- Control Sidebar Toggle Button -->
<!--                     <li>
                        <a href="/erp/shiro/pwdPage" ><i class="fa fa-wrench" ></i>修改密码</a>
                    </li>  -->

                    <li>
                        <a href="/erp/logout" ><i class="fa fa-gears"></i>注销</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>