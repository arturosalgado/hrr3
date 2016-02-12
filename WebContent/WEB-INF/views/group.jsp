<%@page import="java.util.*,java.text.*,com.hrr3.entity.ssr.*,com.hrr3.entity.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <% 
      User u =(User)session.getAttribute("myUserData");
       Hotel h= u.getCurrentHotel();
       String isH = "";
       if (h==null)
       {
       	isH = "noHotel";
       }
       else
       {
       	isH ="";
       }
      %>
    <script>
      var Hotel = "<%=isH%>";
      if (Hotel == "noHotel"){
      alert("Please select a Hotel");
      window.close();
      }
    </script>
    <script>
      // declare it before everything 
       site_url = '${site_url}/';
      //prompt(site_url,site_url);
    </script>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/hrr3/zkau/web/442f0d03/zul/css/zk.wcs">
    <!-- ZK 6.5.4 2013092409 -->
    <script type="text/javascript">
      AU_progressbar = function (id, msg) 
      {
          Boot_progressbox(id, msg, 0, 0, true, true);
      };
    </script>
    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="js/scripts2.js"></script>
    <script src="js/jquery-ui/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="js/jquery-ui/jquery-ui.css">
    <%
      List<SSRInputData> tdrList = (List<SSRInputData>) request.getAttribute("tdrList");
      
      
      int row = 0;
      
      %>     
    <link rel="stylesheet" type="text/css" href="/hrr3/zkau/web/442f0d03/zul/css/zk.wcs">
    <script type="text/javascript" src="/hrr3/zkau/web/442f0d03/js/zk.wpd" charset="UTF-8"></script><script type="text/javascript" charset="UTF-8" src="/hrr3/zkau/web/_zv2013092409/js/zul.lang.wpd"></script>
    <!-- ZK 6.5.4 2013092409 -->
    <script type="text/javascript">
      AU_progressbar = function (id, msg) 
      {
          Boot_progressbox(id, msg, 0, 0, true, true);
      };
    </script>
    
    <script>
    $(document).ready(function(){
    	
    	$("#start-date").datepicker();
    	$("#end-date").datepicker();
    	
    	
    	$("#submitButton").click(function(){
    		
    		$("#data-form").submit();
    		
    	});
    	
    });
    </script>
    
  </head>
  <body class="safari safari537 breeze">
    <div id="xAGQ_" style="width:100%;height:100%;" class="z-page">
      <div style="display:none" id="xAGQ0">
        &nbsp;
        <style id="xAGQ0-real" style="display:none;" class="z-style">
          tr.z-row td.z-row-inner, tr.z-row td.z-cell, tr.z-group td.z-group-inner, tr.z-groupfoot td.z-groupfoot-inner {
          padding: 0px 0px 0px 0px;
          }
          .z-textbox, .z-decimalbox, .z-intbox, .z-longbox, .z-doublebox, .z-auxheader-cnt, div.z-column-cnt, div.z-row-cnt, .z-label {
          font-size: 11px;
          }
        </style>
      </div>
      <div id="xAGQ1" style="width:100%;height:100%;"
        class="z-window-embedded">
        <div class="z-window-embedded-tl">
          <div class="z-window-embedded-tr"></div>
        </div>
        <div class="z-window-embedded-hl">
          <div class="z-window-embedded-hr">
            <div class="z-window-embedded-hm">
              <div id="xAGQ1-cap" class="z-window-embedded-header">Group Input Screen</div>
            </div>
          </div>
        </div>
        <div class="z-window-embedded-cl">
          <div class="z-window-embedded-cr">
            <div class="z-window-embedded-cm">
              <div id="xAGQ1-cave" class="z-window-embedded-cnt" style="overflow: auto; height: 624px;">
                <table id="xAGQ2" class="z-vbox" cellpadding="0" cellspacing="0" border="0" style="height: 607px; width: 164px;">
                  <tbody>
                    <tr valign="top">
                      <td id="xAGQ2-frame" style="width:100%;height:100%" align="left">
                         <form id="data-form"action="" method="post" >
	                        <table id="xAGQ2-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left;width:100%">
	                        
	                         
	                          <tbody>
	                            <tr id="xAGQ3-chdex" valign="top">
	                              <td align="left">
	                                <table id="xAGQ3" style="border: 1px solid #c5c5c5;width:100%;height:70px;" class="z-hbox" cellpadding="0" cellspacing="0" border="0">
	                                  <tbody>
	                                    <tr valign="middle">
	                                      <td id="xAGQ3-frame" style="width: 100%; height: 70px;" align="left">
	                                        <table id="xAGQ3-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
	                                          <tbody>
	                                            <tr valign="middle">
	                                              <td id="xAGQ4-chdex" style="min-width: 1px; width: 3466px;">
	                                                <table id="xAGQ4" class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="width: 3466px;">
	                                                  <tbody>
	                                                    <tr valign="top">
	                                                      <td id="xAGQ4-frame" style="width:100%;height:100%" align="left">
	                                                        <table id="xAGQ4-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
	                                                          <tbody>
	                                                            <tr valign="top">
	                                                              <td id="xAGQ5-chdex" style="min-width: 1px; width: 400px;">
	                                                                <table id="xAGQ5" class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="width: 400px">
	                                                                  <tbody>
	                                                                    <tr valign="middle">
	                                                                      <td id="xAGQ5-frame" style="width:100%;height:100%" align="center">
	                                                                        <table id="xAGQ5-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
	                                                                          <tbody>
	                                                                            <tr valign="middle">
	                                                                              <td id="xAGQ6-chdex" style="height:100%"><span id="xAGQ6" class="z-checkbox"><input type="checkbox" id="xAGQ6-real" checked="checked"><label for="xAGQ6-real" class="z-checkbox-cnt">Comments</label></span></td>
	                                                                              <td id="xAGQ6-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQ7-chdex" style="height:100%"><span id="xAGQ7" class="z-label">
	                                                                                </span>
	                                                                              </td>
	                                                                              <td id="xAGQ7-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQ8-chdex" style="height:100%"><span id="xAGQ8" class="z-checkbox"><input type="checkbox" id="xAGQ8-real" checked="checked"><label for="xAGQ8-real" class="z-checkbox-cnt">Tot</label></span></td>
	                                                                              <td id="xAGQ8-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQ9-chdex" style="height:100%"><span id="xAGQ9" class="z-label">
	                                                                                </span>
	                                                                              </td>
	                                                                              <td id="xAGQ9-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQa-chdex" style="height:100%"><span id="xAGQa" class="z-checkbox"><input type="checkbox" id="xAGQa-real" checked="checked"><label for="xAGQa-real" class="z-checkbox-cnt">Def</label></span></td>
	                                                                              <td id="xAGQa-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQb-chdex" style="height:100%"><span id="xAGQb" class="z-label">
	                                                                                </span>
	                                                                              </td>
	                                                                              <td id="xAGQb-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQc-chdex" style="height:100%"><span id="xAGQc" class="z-checkbox"><input type="checkbox" id="xAGQc-real" checked="checked"><label for="xAGQc-real" class="z-checkbox-cnt">Ten</label></span></td>
	                                                                            </tr>
	                                                                          </tbody>
	                                                                        </table>
	                                                                      </td>
	                                                                    </tr>
	                                                                  </tbody>
	                                                                </table>
	                                                              </td>
	                                                              <td id="xAGQ5-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                              <td id="xAGQd-chdex" style="min-width: 1px; width: 400px;">
	                                                                <table id="xAGQd" class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="width: 400px;">
	                                                                  <tbody>
	                                                                    <tr valign="middle">
	                                                                      <td id="xAGQd-frame" style="width:100%;height:100%" align="center">
	                                                                        <table id="xAGQd-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
	                                                                          <tbody>
	                                                                            <tr valign="middle">
	                                                                              <td id="xAGQe-chdex" style="height:100%"><span id="xAGQe" class="z-label">
	                                                                                Start Date: </span>
	                                                                              </td>
	                                                                              <td id="xAGQe-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQf-chdex" style="height:100%">
	                                                                                <i id="xAGQf" style="width:90px;" class="z-datebox">
	                                                                                  <input id="start-date" name="date1" class="z-datebox-inp" autocomplete="off" value="" type="text" size="11" style="width: 66px;">
	                                                                                  <i id="xAGQf-btn" class="z-datebox-btn">
	                                                                                    <div class="z-datebox-btn-icon"></div>
	                                                                                  </i>
	                                                                                </i>
	                                                                              </td>
	                                                                              <td id="xAGQf-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQg-chdex" style="height:100%"><span id="xAGQg" class="z-label">
	                                                                                End Date: </span>
	                                                                              </td>
	                                                                              <td id="xAGQg-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQh-chdex" style="height:100%">
	                                                                                <i id="xAGQh" style="width:90px;" class="z-datebox">
	                                                                                  <input name="date2" id="end-date" class="z-datebox-inp" autocomplete="off" value="" type="text" size="11" style="width: 66px;">
	                                                                                  <i id="xAGQh-btn" class="z-datebox-btn">
	                                                                                    <div class="z-datebox-btn-icon"></div>
	                                                                                  </i>
	                                                                                </i>
	                                                                              </td>
	                                                                              <td id="xAGQh-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQi-chdex" style="height:100%">
	                                                                              
	                                                                              
	                                                                              <button type="button" id="submitButton" style="width:40px;height:40px;" class="z-button-os">GO</button>
	                                                                              
	                                                                              </td>
	                                                                              <td id="xAGQi-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
	                                                                              <td id="xAGQj-chdex" style="height:100%"><button type="button" id="xAGQj" style="width:40px;height:40px;" class="z-button-os">Print</button></td>
	                                                                            </tr>
	                                                                          </tbody>
	                                                                        </table>
	                                                                      </td>
	                                                                    </tr>
	                                                                  </tbody>
	                                                                </table>
	                                                              </td>
	                                                            </tr>
	                                                          </tbody>
	                                                        </table>
	                                                      </td>
	                                                    </tr>
	                                                  </tbody>
	                                                </table>
	                                              </td>
	                                            </tr>
	                                          </tbody>
	                                        </table>
	                                      </td>
	                                    </tr>
	                                  </tbody>
	                                </table>
	                              </td>
	                            </tr>
	                            <tr id="xAGQ3-chdex2" class="z-vbox-sep">
	                              <td><img style="height:0;width:0"></td>
	                            </tr>
	                            <tr id="xAGQk-chdex" valign="top">
	                              <td align="left">
	                                <table id="xAGQk" style="width:100%;height:100%;" class="z-hbox" cellpadding="0" cellspacing="0" border="0">
	                                  <tbody>
	                                    <tr valign="top">
	                                      <td id="xAGQk-frame" style="width: 100%; height: 569px;" align="left">
	                                        <table id="xAGQk-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
	                                          <tbody>
	                                            <tr valign="top">
	                                              <td id="xAGQl-chdex" style="min-width: 1px; width: 3468px;">
	                                                <div id="xAGQl" style="height: 567px; width: 3466px;" class="z-grid">
	                                                  <div id="xAGQl-head" class="z-grid-header" style="width: 3466px;">
	                                                    <table width="100%" cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; width: 3466px;">
	                                                      <tbody style="visibility:hidden;height:0px">
	                                                        <tr id="xAGQq-hdfaker" class="z-grid-faker">
	                                                          <th id="xAGQw-hdfaker" style="text-align: center; width: 124px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx-hdfaker" style="text-align: center; width: 65px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy-hdfaker" style="text-align: center; width: 39px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz-hdfaker" style="text-align: center; width: 56px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ_0-hdfaker" style="text-align: center; width: 30px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ00-hdfaker" style="text-align: center; width: 64px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ10-hdfaker" style="text-align: center; width: 54px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ20-hdfaker" style="text-align: center; width: 53px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ30-hdfaker" style="text-align: center; width: 57px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ40-hdfaker" style="text-align: center; width: 54px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ50-hdfaker" class="z-column" style="width: 53px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ60-hdfaker" class="z-column" style="width: 57px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ70-hdfaker" class="z-column" style="width: 56px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ80-hdfaker" class="z-column" style="width: 55px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ90-hdfaker" class="z-column" style="width: 59px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQb0-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQc0-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQd0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQe0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQf0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQg0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQi0-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQj0-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQk0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQl0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQm0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQn0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQp0-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq0-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQr0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQs0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQt0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQu0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQw0-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx0-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz0-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ_1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ01-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ21-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ31-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ41-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ51-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ61-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ71-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ91-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQa1-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQb1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQc1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQd1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQe1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQg1-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQh1-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQi1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQj1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQk1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQl1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQn1-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQo1-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQp1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQr1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQs1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQu1-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQv1-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQw1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz1-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ02-hdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ12-hdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ22-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ32-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ42-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ52-hdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq-hdfakerflex" style="width: 0px;"></th>
	                                                        </tr>
	                                                      </tbody>
	                                                      <tbody>
	                                                        <tr id="xAGQm" class="z-auxhead" align="left">
	                                                          <th id="xAGQu" style="text-align:center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQu-cave" class="z-auxheader-cnt" style="text-align:center;">General Information</div>
	                                                          </th>
	                                                          <th id="xAGQv" style="text-align:center;" class="z-auxheader" colspan="9">
	                                                            <div id="xAGQv-cave" class="z-auxheader-cnt" style="text-align:center;">Totals For Group</div>
	                                                          </th>
	                                                          <th id="xAGQa0" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQa0-cave" class="z-auxheader-cnt" style="text-align:center;">GCorp</div>
	                                                          </th>
	                                                          <th id="xAGQh0" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQh0-cave" class="z-auxheader-cnt" style="text-align:center;">GSMERF</div>
	                                                          </th>
	                                                          <th id="xAGQo0" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQo0-cave" class="z-auxheader-cnt" style="text-align:center;">GTour</div>
	                                                          </th>
	                                                          <th id="xAGQv0" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQv0-cave" class="z-auxheader-cnt" style="text-align:center;">GHosp</div>
	                                                          </th>
	                                                          <th id="xAGQ11" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQ11-cave" class="z-auxheader-cnt" style="text-align:center;">GGovt</div>
	                                                          </th>
	                                                          <th id="xAGQ81" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQ81-cave" class="z-auxheader-cnt" style="text-align:center;">GAssoc</div>
	                                                          </th>
	                                                          <th id="xAGQf1" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQf1-cave" class="z-auxheader-cnt" style="text-align:center;">GSports</div>
	                                                          </th>
	                                                          <th id="xAGQm1" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQm1-cave" class="z-auxheader-cnt" style="text-align:center;">GOnline</div>
	                                                          </th>
	                                                          <th id="xAGQt1" style="text-align: center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQt1-cave" class="z-auxheader-cnt" style="text-align:center;">GEntmt</div>
	                                                          </th>
	                                                          <th id="xAGQ_2" style="text-align:center;" class="z-auxheader" colspan="6">
	                                                            <div id="xAGQ_2-cave" class="z-auxheader-cnt" style="text-align:center;">GConv</div>
	                                                          </th>
	                                                        </tr>
	                                                        <tr id="xAGQq" class="z-columns" align="left">
	                                                          <th id="xAGQw" style="text-align: center; width: 120px;" class="z-column">
	                                                            <div id="xAGQw-cave" class="z-column-cnt" style="text-align: center; width: 112px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Comments
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQx" style="text-align: center; width: 61px;" class="z-column">
	                                                            <div id="xAGQx-cave" class="z-column-cnt" style="text-align: center; width: 53px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Exception
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQy" style="text-align: center; width: 35px;" class="z-column">
	                                                            <div id="xAGQy-cave" class="z-column-cnt" style="text-align: center; width: 27px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOW
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQz" style="text-align: center; width: 52px;" class="z-column">
	                                                            <div id="xAGQz-cave" class="z-column-cnt" style="text-align: center; width: 44px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Date
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ_0" style="text-align: center; width: 26px;" class="z-column">
	                                                            <div id="xAGQ_0-cave" class="z-column-cnt" style="text-align: center; width: 18px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              A/F
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ00" style="text-align: center; width: 60px;" class="z-column">
	                                                            <div id="xAGQ00-cave" class="z-column-cnt" style="text-align: center; width: 52px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Tot Occ%
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ10" style="text-align: center; width: 50px;" class="z-column">
	                                                            <div id="xAGQ10-cave" class="z-column-cnt" style="text-align: center; width: 42px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Tot Occ
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ20" style="text-align: center; width: 49px;" class="z-column">
	                                                            <div id="xAGQ20-cave" class="z-column-cnt" style="text-align: center; width: 41px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Tot Rev
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ30" style="text-align: center; width: 53px;" class="z-column">
	                                                            <div id="xAGQ30-cave" class="z-column-cnt" style="text-align: center; width: 45px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Tot ADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ40" style="text-align: center; width: 50px;" class="z-column">
	                                                            <div id="xAGQ40-cave" class="z-column-cnt" style="text-align: center; width: 42px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Def Occ
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ50" class="z-column" style="width: 49px;">
	                                                            <div id="xAGQ50-cave" class="z-column-cnt" style="width: 41px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Def Rev
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ60" class="z-column" style="width: 53px;">
	                                                            <div id="xAGQ60-cave" class="z-column-cnt" style="width: 45px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Def ADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ70" class="z-column" style="width: 52px;">
	                                                            <div id="xAGQ70-cave" class="z-column-cnt" style="width: 44px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Ten Occ
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ80" class="z-column" style="width: 51px;">
	                                                            <div id="xAGQ80-cave" class="z-column-cnt" style="width: 43px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Ten Rev
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ90" class="z-column" style="width: 55px;">
	                                                            <div id="xAGQ90-cave" class="z-column-cnt" style="width: 47px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              Ten ADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQb0" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQb0-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQc0" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQc0-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQd0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQd0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQe0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQe0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQf0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQf0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQg0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQg0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQi0" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQi0-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQj0" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQj0-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQk0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQk0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQl0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQl0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQm0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQm0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQn0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQn0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQp0" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQp0-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQq0" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQq0-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQr0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQr0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQs0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQs0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQt0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQt0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQu0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQu0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQw0" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQw0-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQx0" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQx0-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQy0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQy0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQz0" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQz0-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ_1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ_1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ01" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ01-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ21" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQ21-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ31" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQ31-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ41" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ41-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ51" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ51-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ61" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ61-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ71" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ71-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ91" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQ91-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQa1" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQa1-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQb1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQb1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQc1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQc1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQd1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQd1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQe1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQe1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQg1" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQg1-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQh1" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQh1-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQi1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQi1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQj1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQj1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQk1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQk1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQl1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQl1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQn1" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQn1-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQo1" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQo1-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQp1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQp1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQq1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQq1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQr1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQr1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQs1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQs1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQu1" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQu1-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQv1" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQv1-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQw1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQw1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQx1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQx1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQy1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQy1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQz1" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQz1-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ02" class="z-column" style="width: 36px;">
	                                                            <div id="xAGQ02-cave" class="z-column-cnt" style="width: 28px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ12" class="z-column" style="width: 39px;">
	                                                            <div id="xAGQ12-cave" class="z-column-cnt" style="width: 31px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ22" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ22-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ32" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ32-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              DADR
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ42" class="z-column" style="width: 40px;">
	                                                            <div id="xAGQ42-cave" class="z-column-cnt" style="width: 32px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TOcc
	                                                            </div>
	                                                          </th>
	                                                          <th id="xAGQ52" class="z-column" style="width: 41px;">
	                                                            <div id="xAGQ52-cave" class="z-column-cnt" style="width: 33px;">
	                                                              <div class="z-column-sort-img"></div>
	                                                              TADR
	                                                            </div>
	                                                          </th>
	                                                        </tr>
	                                                      </tbody>
	                                                    </table>
	                                                  </div>
	                                                  <div class="z-grid-header-bg"></div>
	                                                  <div id="xAGQl-body" class="z-grid-body z-word-nowrap" style="overflow-x: hidden; height: 494px; width: 3466px;">
	                                                    <table width="100%" cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; width: 3466px;">
	                                                      <tbody style="visibility:hidden;height:0px">
	                                                        <tr id="xAGQq-bdfaker" class="z-grid-faker">
	                                                          <th id="xAGQw-bdfaker" style="text-align: center; width: 124px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx-bdfaker" style="text-align: center; width: 65px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy-bdfaker" style="text-align: center; width: 39px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz-bdfaker" style="text-align: center; width: 56px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ_0-bdfaker" style="text-align: center; width: 30px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ00-bdfaker" style="text-align: center; width: 64px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ10-bdfaker" style="text-align: center; width: 54px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ20-bdfaker" style="text-align: center; width: 53px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ30-bdfaker" style="text-align: center; width: 57px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ40-bdfaker" style="text-align: center; width: 54px;" class="z-column">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ50-bdfaker" class="z-column" style="width: 53px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ60-bdfaker" class="z-column" style="width: 57px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ70-bdfaker" class="z-column" style="width: 56px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ80-bdfaker" class="z-column" style="width: 55px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ90-bdfaker" class="z-column" style="width: 59px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQb0-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQc0-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQd0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQe0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQf0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQg0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQi0-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQj0-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQk0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQl0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQm0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQn0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQp0-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq0-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQr0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQs0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQt0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQu0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQw0-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx0-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz0-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ_1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ01-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ21-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ31-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ41-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ51-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ61-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ71-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ91-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQa1-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQb1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQc1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQd1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQe1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQg1-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQh1-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQi1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQj1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQk1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQl1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQn1-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQo1-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQp1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQr1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQs1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQu1-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQv1-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQw1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQx1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQy1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQz1-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ02-bdfaker" class="z-column" style="width: 40px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ12-bdfaker" class="z-column" style="width: 43px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ22-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ32-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ42-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQ52-bdfaker" class="z-column" style="width: 44px;">
	                                                            <div style="overflow:hidden"></div>
	                                                          </th>
	                                                          <th id="xAGQq-bdfakerflex"></th>
	                                                        </tr>
	                                                      </tbody>
	                                                      <tbody id="xAGQt" class="z-rows">
	                                                        <tr id="xAGQ62" class="z-row" align="center">
	                                                          <td id="xAGQa2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQa2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQa2" style="width:120px;" class="z-textbox z-textbox-inplace z-textbox-real-readonly z-textbox-readonly" value="Summary Information" type="text" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQb2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQb2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQb2" style="width:40px;" class="z-textbox z-textbox-inplace z-textbox-real-readonly z-textbox-readonly" value="N" type="text" maxlength="1" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQc2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQc2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQc2" class="z-label"></span></div>
	                                                          </td>
	                                                          <td id="xAGQd2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQd2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQd2" class="z-label"></span></div>
	                                                          </td>
	                                                          <td id="xAGQe2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQe2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQe2" class="z-label">F</span></div>
	                                                          </td>
	                                                          <td id="xAGQf2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQf2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQf2" class="z-label">72.368</span></div>
	                                                          </td>
	                                                          <td id="xAGQg2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQg2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQg2" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQh2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQh2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQh2" class="z-label">1,660</span></div>
	                                                          </td>
	                                                          <td id="xAGQi2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQi2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQi2" class="z-label">166</span></div>
	                                                          </td>
	                                                          <td id="xAGQj2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQj2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQj2" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQk2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQk2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQk2" class="z-label">1,660</span></div>
	                                                          </td>
	                                                          <td id="xAGQl2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQl2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQl2" class="z-label">166</span></div>
	                                                          </td>
	                                                          <td id="xAGQm2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQm2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQm2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQn2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQn2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQn2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQo2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQo2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQo2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQp2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQp2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQp2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQq2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQq2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQq2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQr2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQr2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQr2" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQs2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQs2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQs2" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQt2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQt2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQt2" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQu2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQu2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQu2" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQv2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQv2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQv2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQw2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQw2-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQw2" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQx2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQx2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQx2" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQy2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQy2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQy2" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQz2-chdextr" class="z-row-inner">
	                                                            <div id="xAGQz2-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQz2" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ_3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ_3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ_3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ03-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ03-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ03" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ13-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ13-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ13" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ23-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ23-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ23" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ33-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ33-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ33" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ43-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ43-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ43" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ53-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ53-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ53" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ63-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ63-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ63" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ73-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ73-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ73" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ83-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ83-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ83" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ93-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ93-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ93" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQa3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQa3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQa3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQb3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQb3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQb3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQc3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQc3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQc3" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQd3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQd3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQd3" class="z-label">166.00</span></div>
	                                                          </td>
	                                                          <td id="xAGQe3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQe3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQe3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="10" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQf3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQf3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQf3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="166.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQg3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQg3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQg3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQh3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQh3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQh3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQi3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQi3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQi3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQj3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQj3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQj3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQk3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQk3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQk3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQl3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQl3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQl3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQm3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQm3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQm3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQn3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQn3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQn3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQo3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQo3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQo3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQp3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQp3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQp3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQq3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQq3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQq3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQr3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQr3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQr3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQs3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQs3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQs3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQt3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQt3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQt3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQu3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQu3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQu3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQv3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQv3-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQv3" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQw3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQw3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQw3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQx3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQx3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQx3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQy3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQy3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQy3" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQz3-chdextr" class="z-row-inner">
	                                                            <div id="xAGQz3-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQz3" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ_4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ_4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ_4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ04-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ04-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ04" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ14-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ14-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ14" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ24-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ24-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ24" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ34-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ34-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ34" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ44-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ44-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ44" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ54-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ54-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ54" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ64-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ64-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ64" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ74-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ74-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ74" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ84-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ84-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ84" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ94-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ94-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ94" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQa4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQa4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQa4" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                        </tr>
	                                                        <tr id="xAGQ82" class="z-row z-grid-odd" align="center">
	                                                          <td id="xAGQb4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQb4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQb4" style="width:120px;" class="z-textbox z-textbox-inplace" value="" type="text"></div>
	                                                          </td>
	                                                          <td id="xAGQc4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQc4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQc4" style="width:40px;" class="z-textbox z-textbox-inplace" value="N" type="text" maxlength="1"></div>
	                                                          </td>
	                                                          <td id="xAGQd4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQd4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQd4" class="z-label">Sunday</span></div>
	                                                          </td>
	                                                          <td id="xAGQe4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQe4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQe4" class="z-label">08/02/2015</span></div>
	                                                          </td>
	                                                          <td id="xAGQf4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQf4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQf4" class="z-label">A</span></div>
	                                                          </td>
	                                                          <td id="xAGQg4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQg4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQg4" class="z-label">72.368</span></div>
	                                                          </td>
	                                                          <td id="xAGQh4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQh4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQh4" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQi4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQi4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQi4" class="z-label">1,660</span></div>
	                                                          </td>
	                                                          <td id="xAGQj4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQj4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQj4" class="z-label">166</span></div>
	                                                          </td>
	                                                          <td id="xAGQk4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQk4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQk4" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQl4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQl4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQl4" class="z-label">1,660</span></div>
	                                                          </td>
	                                                          <td id="xAGQm4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQm4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQm4" class="z-label">166</span></div>
	                                                          </td>
	                                                          <td id="xAGQn4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQn4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQn4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQo4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQo4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQo4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQp4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQp4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQp4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQq4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQq4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQq4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQr4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQr4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQr4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQs4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQs4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQs4" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQt4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQt4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQt4" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQu4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQu4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQu4" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQv4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQv4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQv4" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQw4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQw4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQw4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQx4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQx4-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQx4" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQy4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQy4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQy4" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQz4-chdextr" class="z-row-inner">
	                                                            <div id="xAGQz4-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQz4" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ_5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ_5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ_5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ05-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ05-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ05" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ15-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ15-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ15" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ25-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ25-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ25" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ35-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ35-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ35" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ45-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ45-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ45" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ55-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ55-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ55" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ65-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ65-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ65" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ75-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ75-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ75" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ85-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ85-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ85" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ95-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ95-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ95" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQa5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQa5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQa5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQb5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQb5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQb5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQc5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQc5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQc5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQd5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQd5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQd5" class="z-label">10</span></div>
	                                                          </td>
	                                                          <td id="xAGQe5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQe5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQe5" class="z-label">166.00</span></div>
	                                                          </td>
	                                                          <td id="xAGQf5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQf5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQf5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="10" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQg5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQg5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQg5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="166.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQh5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQh5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQh5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQi5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQi5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQi5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQj5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQj5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQj5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQk5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQk5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQk5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQl5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQl5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQl5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQm5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQm5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQm5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQn5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQn5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQn5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQo5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQo5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQo5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQp5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQp5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQp5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQq5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQq5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQq5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQr5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQr5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQr5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQs5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQs5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQs5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQt5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQt5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQt5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQu5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQu5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQu5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQv5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQv5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQv5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQw5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQw5-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQw5" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQx5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQx5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQx5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQy5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQy5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQy5" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQz5-chdextr" class="z-row-inner">
	                                                            <div id="xAGQz5-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQz5" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ_6-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ_6-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ_6" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ06-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ06-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ06" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ16-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ16-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ16" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ26-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ26-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ26" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ36-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ36-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ36" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ46-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ46-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ46" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ56-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ56-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ56" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ66-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ66-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ66" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ76-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ76-cell" class="z-row-cnt z-overflow-hidden"><span id="xAGQ76" class="z-label">0</span></div>
	                                                          </td>
	                                                          <td id="xAGQ86-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ86-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ86" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQ96-chdextr" class="z-row-inner">
	                                                            <div id="xAGQ96-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQ96" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQa6-chdextr" class="z-row-inner">
	                                                            <div id="xAGQa6-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQa6" style="width:40px;" class="z-intbox z-intbox-inplace z-intbox-real-readonly z-intbox-readonly" value="0" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                          <td id="xAGQb6-chdextr" class="z-row-inner">
	                                                            <div id="xAGQb6-cell" class="z-row-cnt z-overflow-hidden"><input id="xAGQb6" style="width:40px;" class="z-decimalbox z-decimalbox-inplace z-decimalbox-real-readonly z-decimalbox-readonly" value="0.00" type="text" size="11" readonly="readonly"></div>
	                                                          </td>
	                                                        </tr>
	                                                      </tbody>
	                                                      <tbody id="xAGQl-empty" class="z-grid-empty-body" style="display:none">
	                                                        <tr>
	                                                          <td colspan="1"></td>
	                                                        </tr>
	                                                      </tbody>
	                                                    </table>
	                                                  </div>
	                                                  <div id="xAGQl-frozen" class="z-grid-frozen">
	                                                    <div id="xAGQo" style="height: 17px; background: rgb(223, 222, 216);" class="z-frozen">
	                                                      <div id="xAGQo-cave" class="z-frozen-body" style="height: 17px; width: 876px;">
	                                                        <div id="xAGQp" style="padding: 0 10px;" class="z-div"></div>
	                                                      </div>
	                                                      <div id="xAGQo-scrollX" class="z-frozen-inner" tabindex="-1" style="height: 17px; width: 2590px;">
	                                                        <div style="height: 17px; width: 5540px;"></div>
	                                                      </div>
	                                                      <div class="z-clear"></div>
	                                                    </div>
	                                                  </div>
	                                                </div>
	                                              </td>
	                                            </tr>
	                                          </tbody>
	                                        </table>
	                                      </td>
	                                    </tr>
	                                  </tbody>
	                                </table>
	                              </td>
	                            </tr>
	                          </tbody>
	                        
	                        
	                        </table>
                          </form>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="z-window-embedded-bl">
          <div class="z-window-embedded-br"></div>
        </div>
      </div>
    </div>
    <noscript>
      &lt;div class="noscript"&gt;&lt;p&gt;Sorry, JavaScript must be enabled.&lt;br/&gt;Change your browser options, then &lt;a href=""&gt;try again&lt;/a&gt;.&lt;/p&gt;&lt;/div&gt;
    </noscript>
    <div style="top: -1000px; left: -1000px; position: absolute; visibility: hidden; border: none; width: 50px; height: 50px; overflow: scroll;"></div>
    <div style="left: -1000px; top: -1000px; position: absolute; visibility: hidden; border: none; white-space: nowrap; word-spacing: 0px; direction: ltr; text-overflow: clip; text-transform: none; text-shadow: none; text-indent: 0px; text-decoration: none; text-align: center; line-height: normal; letter-spacing: normal; font-style: normal; font-weight: bold; font-size: 11px; font-family: arial, sans-serif;">
      <div class="z-column-sort-img"></div>
      Comments
    </div>
  </body>
</html>