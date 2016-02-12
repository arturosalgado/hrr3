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
      	 site_url = '${site_url}/ssrcontrollerajax';
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
      
      
      
      
   </head>
   <body class="safari safari537 breeze">
   
   <%
   
   List<SSRInputData> tdrList = (List<SSRInputData>) request.getAttribute("tdrList");
   
  
  int row = 0;
   
   %>
   
 
   

      <div id="mXDP_" style="width:100%;height:100%;" class="z-page">
         <div style="display:none" id="mXDP0">
            &nbsp;
            <style id="mXDP0-real" style="display:none;" class="z-style">
               tr.z-row td.z-row-inner, tr.z-row td.z-cell, tr.z-group td.z-group-inner, tr.z-groupfoot td.z-groupfoot-inner {
               padding: 0px 0px 0px 0px;
               }
               .z-textbox, .z-decimalbox, .z-intbox, .z-longbox, .z-doublebox, .z-auxheader-cnt, div.z-column-cnt, div.z-row-cnt, .z-label {
               font-size: 11px;
               }
            </style>
         </div>
         <div id="mXDP1" class="z-window-embedded" style="">
            <div class="z-window-embedded-tl">
               <div class="z-window-embedded-tr"></div>
            </div>
            <div class="z-window-embedded-hl">
               <div class="z-window-embedded-hr">
                  <div class="z-window-embedded-hm">
                     <div id="mXDP1-cap" class="z-window-embedded-header">
                        <div id="mXDP1-close" class="z-window-embedded-icon z-window-embedded-close">
                           <div class="z-window-embedded-icon-img"></div>
                        </div>
                        <div id="mXDP1-max" class="z-window-embedded-icon z-window-embedded-max">
                           <div class="z-window-embedded-icon-img"></div>
                        </div>
                        SSR Input Screen
                     </div>
                  </div>
               </div>
            </div>
<div class="z-window-embedded-cl">
   <div class="z-window-embedded-cr">
      <div class="z-window-embedded-cm">
         <div id="mXDP1-cave" style="overflow:scroll; scroll-x:" class="z-window-embedded-cnt">
            <table id="mXDP2" class="z-vbox" cellpadding="0" cellspacing="0" border="0" style="height: 678px; width: 1391px;">
<tbody>
   <tr valign="top">
      <td id="mXDP2-frame" style="width:100%;height:100%" align="left">
<table id="mXDP2-real" style="border:0px solid red" cellpadding="0" cellspacing="0" border="0" style="text-align:left;width:100%">
<tbody>
   <tr id="mXDP3-chdex" valign="top" style="width: 1391px;">
<td align="left" style="min-width: 1px;">
<table id="mXDP3" style="border: 1px solid rgb(197, 197, 197); height: 70px; width: 1389px;" class="z-hbox" cellpadding="0" cellspacing="0" border="0">
<tbody>
   <tr valign="middle">
      <td id="mXDP3-frame" style="width: 100%; height: 70px;" align="center">
<table id="mXDP3-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
<tbody>
   <tr valign="middle">
      <td id="mXDP4-chdex" style="min-width: 1px; width: 1387px;">
<table id="mXDP4"  style="border:0px solid blue"class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="width: 1387px;">
<tbody>
   <tr valign="top">
      <td id="mXDP4-frame" style="width:100%;height:100%" align="left">
     <table id="mXDP4-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
        <tbody>
           <tr valign="top">
              <td id="mXDP5-chdex" style="min-width: 1px; width: 1387px;">
                 <table id="mXDP5" class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="width: 1387px;">
                    <tbody>
                       <tr valign="middle">
                          <td id="mXDP5-frame" style="width:100%;height:100%" align="center">
                             <form action = "ssrcontroller" method="post" id="dateForm" >
                             <table id="mXDP5-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
                                <tbody>
                                   <tr valign="middle">
                                      <td id="mXDP6-chdex" style="height:100%"><span id="mXDP6" class="z-label">
                                         Start Date : </span>
                                      </td>
                                      <td id="mXDP6-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
                                      <td id="mXDP7-chdex" style="height:100%">
                                         <i id="mXDP7" style="width:100px;" class="z-datebox">
                                            <input id="datepicker1" name="date1" class="z-datebox-inp" autocomplete="off" value="${date1}" type="text" size="11" style="width: 76px;">
                                            <i id="calendar-icon-2" class="z-datebox-btn calendar-icon">
                                               <div class="z-datebox-btn-icon"></div>
                                            </i>
                                            <div id="mXDP7-pp" class="z-datebox-pp" style="display: none; z-index: 1800; height: auto; width: auto; position: absolute; top: 82px; left: 595.5px;" tabindex="-1">
                                               <div id="_z_0" class="z-calendar">
                                                  <table style="table-layout: fixed" width="100%" cellpadding="0" cellspacing="0" border="0">
                                                     <tbody>
                                                        <tr>
                                                           <td id="_z_0-tdl" class="z-calendar-tdl ">
                                                              <div class="z-calendar-left">
                                                                 <div id="_z_0-ly" class="z-calendar-left-icon"></div>
                                                              </div>
                                                           </td>
                                                           <td>
                                                              <table class="z-calendar-calctrl" width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                 <tbody>
                                                                    <tr>
                                                                       <td id="_z_0-title" class="z-calendar-title"><span id="_z_0-tm" class="z-calendar-ctrler">Aug</span> <span id="_z_0-ty" class="z-calendar-ctrler">2015</span></td>
                                                                    </tr>
                                                                 </tbody>
                                                              </table>
                                                           </td>
                                                           <td id="_z_0-tdr" class="z-calendar-tdr ">
                                                              <div class="z-calendar-right">
                                                                 <div id="_z_0-ry" class="z-calendar-right-icon"></div>
                                                              </div>
                                                           </td>
                                                        </tr>
                                                        <tr>
                                                           <td colspan="3">
                                                              <table id="_z_0-mid" class="z-calendar-calday" width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                 <tbody>
                                                                    <tr class="z-calendar-caldow">
                                                                       <td class="z-calendar-wkend">Sun</td>
                                                                       <td class="z-calendar-wkday">Mon</td>
                                                                       <td class="z-calendar-wkday">Tue</td>
                                                                       <td class="z-calendar-wkday">Wed</td>
                                                                       <td class="z-calendar-wkday">Thu</td>
                                                                       <td class="z-calendar-wkday">Fri</td>
                                                                       <td class="z-calendar-wkend">Sat</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w0">
                                                                       <td class="z-calendar-wkend z-calendar-outside" _dt="26">26</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="27">27</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="28">28</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="29">29</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="30">30</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="31">31</td>
                                                                       <td class="z-calendar-wkend z-calendar-seld" _dt="1">1</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w1">
                                                                       <td class="z-calendar-wkend" _dt="2">2</td>
                                                                       <td class="z-calendar-wkday" _dt="3">3</td>
                                                                       <td class="z-calendar-wkday" _dt="4">4</td>
                                                                       <td class="z-calendar-wkday" _dt="5">5</td>
                                                                       <td class="z-calendar-wkday" _dt="6">6</td>
                                                                       <td class="z-calendar-wkday" _dt="7">7</td>
                                                                       <td class="z-calendar-wkend" _dt="8">8</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w2">
                                                                       <td class="z-calendar-wkend" _dt="9">9</td>
                                                                       <td class="z-calendar-wkday" _dt="10">10</td>
                                                                       <td class="z-calendar-wkday" _dt="11">11</td>
                                                                       <td class="z-calendar-wkday" _dt="12">12</td>
                                                                       <td class="z-calendar-wkday" _dt="13">13</td>
                                                                       <td class="z-calendar-wkday" _dt="14">14</td>
                                                                       <td class="z-calendar-wkend" _dt="15">15</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w3">
                                                                       <td class="z-calendar-wkend" _dt="16">16</td>
                                                                       <td class="z-calendar-wkday" _dt="17">17</td>
                                                                       <td class="z-calendar-wkday" _dt="18">18</td>
                                                                       <td class="z-calendar-wkday" _dt="19">19</td>
                                                                       <td class="z-calendar-wkday" _dt="20">20</td>
                                                                       <td class="z-calendar-wkday" _dt="21">21</td>
                                                                       <td class="z-calendar-wkend" _dt="22">22</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w4">
                                                                       <td class="z-calendar-wkend" _dt="23">23</td>
                                                                       <td class="z-calendar-wkday" _dt="24">24</td>
                                                                       <td class="z-calendar-wkday" _dt="25">25</td>
                                                                       <td class="z-calendar-wkday" _dt="26">26</td>
                                                                       <td class="z-calendar-wkday" _dt="27">27</td>
                                                                       <td class="z-calendar-wkday" _dt="28">28</td>
                                                                       <td class="z-calendar-wkend" _dt="29">29</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_0-w5">
                                                                       <td class="z-calendar-wkend" _dt="30">30</td>
                                                                       <td class="z-calendar-wkday" _dt="31">31</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="1">1</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="2">2</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="3">3</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="4">4</td>
                                                                       <td class="z-calendar-wkend z-calendar-outside" _dt="5">5</td>
                                                                    </tr>
                                                                 </tbody>
                                                              </table>
                                                              <button id="_z_0-a" tabindex="-1" onclick="return false;" href="javascript:;" class="z-focus-a"></button>
                                                           </td>
                                                        </tr>
                                                     </tbody>
                                                  </table>
                                               </div>
                                               <i id="_z_1" class="z-timebox" style="display: none;">
                                                  <input id="_z_1-real" class="z-timebox-inp" value="" type="text">
                                                  <i id="_z_1-btn" class="z-timebox-btn">
                                                     <div id="_z_1-btn-up" class="z-timebox-btn-upper">
                                                        <div class="z-timebox-btn-up-icon"></div>
                                                     </div>
                                                     <div id="_z_1-btn-down" class="z-timebox-btn-lower">
                                                        <div class="z-timebox-btn-down-icon"></div>
                                                     </div>
                                                  </i>
                                               </i>
                                            </div>
                                         </i>
                                      </td>
                                      <td id="mXDP7-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
                                      <td id="mXDP8-chdex" style="height:100%"><span id="mXDP8" class="z-label">
                                         End Date: </span>
                                      </td>
                                      <td id="mXDP8-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
                                      <td id="mXDP9-chdex" style="height:100%">
                                         <i id="mXDP9" style="width:100px;" class="z-datebox">
                                            <input id="datepicker2" name="date2"class="z-datebox-inp" autocomplete="off" value="${date2}" type="text" size="11" style="width: 76px;">
                                            <i id="calendar-icon-1" class="z-datebox-btn calendar-icon">
                                               <div class="z-datebox-btn-icon"></div>
                                            </i>
                                            <div id="mXDP9-pp" class="z-datebox-pp" style="display: none; z-index: 1800; height: auto; width: auto; position: absolute; top: 82px; left: 752.5px;" tabindex="-1">
                                               <div id="_z_2" class="z-calendar">
                                                  <table style="table-layout: fixed" width="100%" cellpadding="0" cellspacing="0" border="0">
                                                     <tbody>
                                                        <tr>
                                                           <td id="_z_2-tdl" class="z-calendar-tdl ">
                                                              <div class="z-calendar-left">
                                                                 <div id="_z_2-ly" class="z-calendar-left-icon"></div>
                                                              </div>
                                                           </td>
                                                           <td>
                                                              <table class="z-calendar-calctrl" width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                 <tbody>
                                                                    <tr>
                                                                       <td id="_z_2-title" class="z-calendar-title"><span id="_z_2-tm" class="z-calendar-ctrler">Aug</span> <span id="_z_2-ty" class="z-calendar-ctrler">2015</span></td>
                                                                    </tr>
                                                                 </tbody>
                                                              </table>
                                                           </td>
                                                           <td id="_z_2-tdr" class="z-calendar-tdr ">
                                                              <div class="z-calendar-right">
                                                                 <div id="_z_2-ry" class="z-calendar-right-icon"></div>
                                                              </div>
                                                           </td>
                                                        </tr>
                                                        <tr>
                                                           <td colspan="3">
                                                              <table id="_z_2-mid" class="z-calendar-calday" width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                 <tbody>
                                                                    <tr class="z-calendar-caldow">
                                                                       <td class="z-calendar-wkend">Sun</td>
                                                                       <td class="z-calendar-wkday">Mon</td>
                                                                       <td class="z-calendar-wkday">Tue</td>
                                                                       <td class="z-calendar-wkday">Wed</td>
                                                                       <td class="z-calendar-wkday">Thu</td>
                                                                       <td class="z-calendar-wkday">Fri</td>
                                                                       <td class="z-calendar-wkend">Sat</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w0">
                                                                       <td class="z-calendar-wkend z-calendar-outside" _dt="26">26</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="27">27</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="28">28</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="29">29</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="30">30</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="31">31</td>
                                                                       <td class="z-calendar-wkend" _dt="1">1</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w1">
                                                                       <td class="z-calendar-wkend" _dt="2">2</td>
                                                                       <td class="z-calendar-wkday" _dt="3">3</td>
                                                                       <td class="z-calendar-wkday" _dt="4">4</td>
                                                                       <td class="z-calendar-wkday" _dt="5">5</td>
                                                                       <td class="z-calendar-wkday" _dt="6">6</td>
                                                                       <td class="z-calendar-wkday" _dt="7">7</td>
                                                                       <td class="z-calendar-wkend" _dt="8">8</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w2">
                                                                       <td class="z-calendar-wkend" _dt="9">9</td>
                                                                       <td class="z-calendar-wkday" _dt="10">10</td>
                                                                       <td class="z-calendar-wkday" _dt="11">11</td>
                                                                       <td class="z-calendar-wkday" _dt="12">12</td>
                                                                       <td class="z-calendar-wkday" _dt="13">13</td>
                                                                       <td class="z-calendar-wkday" _dt="14">14</td>
                                                                       <td class="z-calendar-wkend" _dt="15">15</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w3">
                                                                       <td class="z-calendar-wkend" _dt="16">16</td>
                                                                       <td class="z-calendar-wkday" _dt="17">17</td>
                                                                       <td class="z-calendar-wkday" _dt="18">18</td>
                                                                       <td class="z-calendar-wkday" _dt="19">19</td>
                                                                       <td class="z-calendar-wkday" _dt="20">20</td>
                                                                       <td class="z-calendar-wkday" _dt="21">21</td>
                                                                       <td class="z-calendar-wkend" _dt="22">22</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w4">
                                                                       <td class="z-calendar-wkend" _dt="23">23</td>
                                                                       <td class="z-calendar-wkday" _dt="24">24</td>
                                                                       <td class="z-calendar-wkday" _dt="25">25</td>
                                                                       <td class="z-calendar-wkday" _dt="26">26</td>
                                                                       <td class="z-calendar-wkday" _dt="27">27</td>
                                                                       <td class="z-calendar-wkday" _dt="28">28</td>
                                                                       <td class="z-calendar-wkend" _dt="29">29</td>
                                                                    </tr>
                                                                    <tr class="z-calendar-caldayrow" id="_z_2-w5">
                                                                       <td class="z-calendar-wkend" _dt="30">30</td>
                                                                       <td class="z-calendar-wkday z-calendar-seld" _dt="31">31</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="1">1</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="2">2</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="3">3</td>
                                                                       <td class="z-calendar-wkday z-calendar-outside" _dt="4">4</td>
                                                                       <td class="z-calendar-wkend z-calendar-outside" _dt="5">5</td>
                                                                    </tr>
                                                                 </tbody>
                                                              </table>
                                                              <button id="_z_2-a" tabindex="-1" onclick="return false;" href="javascript:;" class="z-focus-a"></button>
                                                           </td>
                                                        </tr>
                                                     </tbody>
                                                  </table>
                                               </div>
                                               <i id="_z_3" class="z-timebox" style="display: none;">
                                                  <input id="_z_3-real" class="z-timebox-inp" value="" type="text">
                                                  <i id="_z_3-btn" class="z-timebox-btn">
                                                     <div id="_z_3-btn-up" class="z-timebox-btn-upper">
                                                        <div class="z-timebox-btn-up-icon"></div>
                                                     </div>
                                                     <div id="_z_3-btn-down" class="z-timebox-btn-lower">
                                                        <div class="z-timebox-btn-down-icon"></div>
                                                     </div>
                                                  </i>
                                               </i>
                                            </div>
                                         </i>
                                      </td>
                                      <td id="mXDP9-chdex2" class="z-hbox-sep"><img style="height:0;width:0"></td>
                                      <td id="mXDPa-chdex" style="height:100%"><button type="submit" id="submit" style="width:40px;height:40px;" class="z-button-os">GO</button></td>
                                                                        </tr>
                                                                     </tbody>
                                                                  </table>
                                                               	</form>
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
<tr id="mXDP3-chdex2" class="z-vbox-sep">
   <td><img style="height:0;width:0"></td>
</tr>
<tr id="mXDPb-chdex" style="border:0px solid green" valign="top" style="height: 601px; width: 1391px;">
<td align="left" style="min-width: 1px;">
<div style="">
	<table id="mXDPb" class="z-hbox" cellpadding="0" cellspacing="0" border="0" style="height: 601px; width: px;overflow: scroll">
<tbody>
   <tr valign="top">
      <td id="mXDPb-frame" style="width: 100%; height: 601px;" align="left">
<table id="mXDPb-real" cellpadding="0" cellspacing="0" border="0" style="text-align:left">
<tbody>
   <tr valign="top">
      <td id="mXDPc-chdex" style="min-width: 1px; height: 601px; width: 1391px;">
<div id="mXDPc" style="height: 599px; width: 1389px;background-image:none!important" class="z-grid">
<div id="mXDPc-head" class="z-grid-header" style="width: 1372px;">
<table style="border:0px solid purple" width="100%" cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; ">
  <tbody style="visibility:hidden;height:0px">
     <tr id="mXDPh-hdfaker" class="z-grid-faker">
        <th id="mXDPq-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPr-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPs-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPt-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPu-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPv-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPx-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPz-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP_0-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP00-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP10-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP20-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP30-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP40-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP50-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP60-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP70-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP80-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP90-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPa0-hdfaker" style="display: none; width: 0px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPb0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPc0-hdfaker" style="text-align: center; width:px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPe0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPg0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPi0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPj0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPl0-hdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPn0-hdfaker" class="z-column" style="width: px;">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPh-hdfakerflex" style="width: px;"></th>
     </tr>
  </tbody>
  <tbody>
     <tr id="mXDPd" class="z-auxhead" align="left">
        <th id="mXDPl" style="text-align:center;" class="z-auxheader" colspan="5">
           <div id="mXDPl-cave" class="z-auxheader-cnt" style="text-align:center;">General Information</div>
        </th>
        <th id="mXDPm" style="text-align:center;" class="z-auxheader" colspan="3">
           <div id="mXDPm-cave" class="z-auxheader-cnt" style="text-align:center;">Totals</div>
        </th>
        <th id="mXDPn" style="text-align:center;" class="z-auxheader" colspan="11">
           <div id="mXDPn-cave" class="z-auxheader-cnt" style="text-align:center;">Rate Categories</div>
        </th>
        <th id="mXDPo" style="text-align: center;" class="z-auxheader" colspan="7">
           <div id="mXDPo-cave" class="z-auxheader-cnt" style="text-align:center;">Segment Information</div>
        </th>
        <th id="mXDPp" style="text-align:center;" class="z-auxheader" colspan="2">
           <div id="mXDPp-cave" class="z-auxheader-cnt" style="text-align:center;">MAR Information</div>
        </th>
     </tr>
     <tr id="mXDPh" class="z-columns" align="left">
        <th id="mXDPq" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPq-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              Comments
           </div>
        </th>
        <th id="mXDPr" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPr-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              Exception
           </div>
        </th>
        <th id="mXDPs" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPs-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              DOW
           </div>
        </th>
        <th id="mXDPt" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPt-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              Date
           </div>
        </th>
        <th id="mXDPu" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPu-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              A/F
           </div>
        </th>
        <th id="mXDPv" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPv-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPw" class="z-label">Tot <br>
              Occ%</span>
           </div>
        </th>
        <th id="mXDPx" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPx-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPy" class="z-label">Occ <br>
              Rms</span>
           </div>
        </th>
        <th id="mXDPz" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPz-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              LRR
           </div>
        </th>
        <th id="mXDP_0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP_0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              A/0
           </div>
        </th>
        <th id="mXDP00" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP00-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              B/1
           </div>
        </th>
        <th id="mXDP10" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP10-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              C/2
           </div>
        </th>
        <th id="mXDP20" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP20-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              D/3
           </div>
        </th>
        <th id="mXDP30" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP30-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              E/4
           </div>
        </th>
        <th id="mXDP40" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP40-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              F/5
           </div>
        </th>
        <th id="mXDP50" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP50-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              G/6
           </div>
        </th>
        <th id="mXDP60" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP60-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              H/7
           </div>
        </th>
        <th id="mXDP70" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP70-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              I/8
           </div>
        </th>
        <th id="mXDP80" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP80-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              HP1
           </div>
        </th>
        <th id="mXDP90" style="text-align: center; width: px;" class="z-column">
           <div id="mXDP90-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              HP2
           </div>
        </th>
        <th id="mXDPa0" style="display: none; width: 0px;" class="z-column">
           <div id="mXDPa0-cave" class="z-column-cnt">
              <div class="z-column-sort-img"></div>
              Oversell Factor
           </div>
        </th>
        <th id="mXDPb0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPb0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              Trans
           </div>
        </th>
        <th id="mXDPc0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPc0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPd0" class="z-label">Group <br>
              Block</span>
           </div>
        </th>
        <th id="mXDPe0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPe0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPf0" class="z-label">Group <br>
              P/U</span>
           </div>
        </th>
        <th id="mXDPg0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPg0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPh0" class="z-label">Group <br>
              Remain</span>
           </div>
        </th>
        <th id="mXDPi0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPi0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              Contract
           </div>
        </th>
        <th id="mXDPj0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPj0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPk0" class="z-label">Demand <br>
              TD</span>
           </div>
        </th>
        <th id="mXDPl0" style="text-align: center; width: px;" class="z-column">
           <div id="mXDPl0-cave" class="z-column-cnt" style="text-align: center; width: px;">
              <div class="z-column-sort-img"></div>
              <span id="mXDPm0" class="z-label">Price <br>
              TD</span>
           </div>
        </th>
        <th id="mXDPn0" class="z-column" style="width: px;">
           <div id="mXDPn0-cave" class="z-column-cnt" style="width: px;">
                  <div class="z-column-sort-img"></div>
                  <span id="mXDPo0" class="z-label">SSR <br>
                  MAR</span>
               </div>
            </th>
         </tr>
      </tbody>
   </table>
</div>
<div class="z-grid-header-bg"></div>
<div id="mXDPc-body" class="z-grid-body z-word-nowrap" style="overflow-x: hidden; height: 514px; width: px;">
<table width="100%" cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; width: px;">
  <tbody style="visibility:hidden;height:0px">
     <tr id="mXDPh-bdfaker" class="z-grid-faker">
        <th id="mXDPq-bdfaker" style="text-align: center; width: px;" class="z-column ">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPr-bdfaker" style="text-align: center; width: px;" class="z-column " >
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPs-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPt-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPu-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPv-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPx-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPz-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP_0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP00-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP10-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP20-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP30-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP40-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP50-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP60-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP70-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP80-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDP90-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPa0-bdfaker" style="display: none; width: 0px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPb0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPc0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPe0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPg0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPi0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPj0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPl0-bdfaker" style="text-align: center; width: px;" class="z-column">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPn0-bdfaker" class="z-column" style="width: px;">
           <div style="overflow:hidden"></div>
        </th>
        <th id="mXDPh-bdfakerflex"></th>
     </tr>
  </tbody>
  <tbody id="mXDPk" class="z-rows">

<c:forEach items="${tdrList}" var="record">
<% String type= "odd";
if (row%2==0)
	type="even";
%>                               
<tr id="rEsPr0" class="z-row z-grid-<%=type%>">
	<td id="rEsPk1-chdextr" class="z-row-inner">
		<div id="rEsPk1-cell" class="z-row-cnt z-overflow-hidden">
			<input  
			   id="cell_<%=row %>_-1" 
			   col="-1"
			   row="<%= row %>"
			   update_id="${record.getId()}" 
			style="width:120px;" class="changes keylistener   z-textbox z-textbox-inplace" 
			value="${record.getComment()}" type="text">
		</div>
	</td>
	<td id="rEsPl1-chdextr" class="z-row-inner">
		<div id="rEsPl1-cell" class="z-row-cnt z-overflow-hidden">
			<input
			id="cell_<%=row %>_0" 
			col="0"
			row="<%= row %>"
			update_id="${record.getId()}" 
			style="width:40px;" class="changes editormode keylistener z-textbox z-textbox-inplace" 
			value="${record.isException()}" type="text" maxlength="1">
		</div>
	</td>
	<td id="rEsPm1-chdextr" class="z-row-inner">
	<div id="rEsPm1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPm1" class="z-label">
	${record.getDow()}
	</span>
	</div></td><td id="rEsPn1-chdextr" class="z-row-inner">
	<div id="rEsPn1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPn1" class="z-label">
	${record.getStatdate()}
	</span></div></td>
	<td id="rEsPo1-chdextr" class="z-row-inner"><div id="rEsPo1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPo1" class="z-label">${record.getIsActualLabel()}</span></div></td>
	<td id="rEsPp1-chdextr" class="z-row-inner">
	<div id="rEsPp1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPp1" class="z-label">
	<c:out value="${record.getOccpcnt()}"/>
	</span></div></td>
	<td id="rEsPq1-chdextr" class="z-row-inner">
	<div id="rEsPq1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPq1" class="z-label">
	<c:out value="${record.getTotOccS()}"/>
	</span></div></td>
	<td id="rEsPr1-chdextr" class="z-row-inner">
	<div id="rEsPr1-cell" class="z-row-cnt z-overflow-hidden">
	<span id="LRR-${record.getId()}" class="z-label">
	<c:out value="${record.getLrr()}"/>
	</span></div></td>
	<td id="rEsPs1-chdextr" class="z-row-inner">
	<div id="" class="z-row-cnt z-overflow-hidden">
	
	
	<input id="cell_<%=row %>_1" style="width:25px;" 
	update_id="${record.getId()}" 
	 class="keylistener editormode changes z-textbox z-textbox-inplace"
	  row="<%= row %>"
	  col="1"  
	  value="${record.getA1()}" type="text">
	</div></td>
	<td id="rEsPt1-chdextr" class="z-row-inner">
	<div id="rEsPt1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_2" style="width:25px;" update_id="${record.getId()}" 
	 row="<%= row %>" col="2" class="changes keylistener z-textbox z-textbox-inplace"
	 value="${record.getB2()}" type="text"></div></td>
	 
	<td id="rEsPu1-chdextr" class="z-row-inner"><div  id="rEsPu1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_3" style="width:25px;" update_id="${record.getId()}" row="<%= row %>" col="3" class="changes keylistener z-textbox z-textbox-inplace" 
	value="${record.getC3()}" type="text"></div></td>
	
	<td id="rEsPv1-chdextr" class="z-row-inner">
	<div id="rEsPv1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_4" style="width:25px;" update_id="${record.getId()}" 
	row="<%= row %>" col="4"   class="changes keylistener z-textbox z-textbox-inplace" 
	value="${record.getD4()}" type="text"></div></td>
	
	<td id="rEsPw1-chdextr" class="z-row-inner">
	<div id="rEsPw1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_5" style="width:25px;" update_id="${record.getId()}"
	 row="<%= row %>" col="5"  class="changes keylistener z-textbox z-textbox-inplace" 
	value="${record.getE5()}" type="text"></div></td>
	
	
	<td id="rEsPx1-chdextr" class="z-row-inner">
	<div id="rEsPx1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_6" style="width:25px;"
	 update_id="${record.getId()}" row="<%= row %>" col="6"  class="changes keylistener z-textbox z-textbox-inplace" 
	value="${record.getF6()}" type="text"></div></td>
	
	<td id="rEsPy1-chdextr" class="z-row-inner"><div id="rEsPy1-cell" 
	class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_7" style="width:25px;" 
	update_id="${record.getId()}" row="<%= row %>" col="7"  
	class="changes keylistener z-textbox z-textbox-inplace"
	 value="${record.getG7()}" type="text"></div></td>
	
	
	<td id="rEsPz1-chdextr" class="z-row-inner">
	<div id="rEsPz1-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_8" style="width:25px;" update_id="${record.getId()}" row="<%= row %>" col="8"  class="changes keylistener z-textbox z-textbox-inplace" 
	value="${record.getH8()}" type="text"></div></td>
	
	<td id="rEsP_2-chdextr" class="z-row-inner">
	<div id="rEsP_2-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_9" style="width:25px;" 
	update_id="${record.getId()}" row="<%= row %>" col="9"  class="keylistener changes z-textbox z-textbox-inplace" 
	value="${record.getI9()}" type="text"></div></td>
	
	
	<td id="rEsP02-chdextr" class="z-row-inner">
	<div id="rEsP02-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_10" style="width:25px;" update_id="${record.getId()}"
	 row="<%= row %>" col="10"  class="keylistener changes z-textbox z-textbox-inplace" 
	value="${record.getHp1()}" type="text"></div></td>
	
	
	<td id="rEsP12-chdextr" class="z-row-inner"><div id="rEsP12-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_11" style="width:25px;"
	 update_id="${record.getId()}" row="<%= row %>" col="11" 
	  class="keylistener changes z-textbox z-textbox-inplace"
	 value="${record.getHp2()}" type="text">
	 </div>
	 </td>
	
	
	
	
	
	<td id="rEsP32-chdextr" class="z-row-inner">
	<div id="rEsP32-cell" class="z-row-cnt z-overflow-hidden">
	
	
	<input  id="cell_<%=row %>_12"  row="<%=row %>"
	 col="12"
	 update_id="${record.getId()}"
	 style="width:70px;" class="changes keylistener z-decimalbox z-decimalbox-inplace" 
	 value="${record.getOversellFactor()}" type="text" size="11">
	</div>
	</td>
	
	<td id="rEsP42-chdextr" class="z-row-inner">
	<div id="rEsP42-cell" class="z-row-cnt z-overflow-hidden">
	<input  id="cell_<%=row %>_13"  row="<%=row%>" 
	col="13"
	update_id="${record.getId()}"
	style="width:70px;" class="changes keylistener z-decimalbox z-decimalbox-inplace" 
	value="${record.getRotbGroupS()}"  type="text" size="11">
	</div>
	</td>
	
	
	<td id="rEsP52-chdextr" class="z-row-inner"><div id="rEsP52-cell" class="z-row-cnt z-overflow-hidden">
	<input  id="cell_<%=row %>_14"
	 row="<%=row%>"
	 col="14" 
	 update_id="${record.getId()}"
	 style="width:70px;"
	  class="changes keylistener z-decimalbox z-decimalbox-inplace" 
	  value="<c:out value="${record.getGrpPickedupS()}"/>" type="text" size="11">
	</div>
	</td>
	
	
	<td id="rEsP62-chdextr" class="z-row-inner"><div id="rEsP62-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_15"
	row="<%=row%>" 
	update_id="${record.getId()}"
	style="width:70px;"
	col="15" 
	class="changes keylistener z-decimalbox z-decimalbox-inplace" value="<c:out value="${record.getGrpRmsRemS()}"/>" type="text" size="11">
	</div>
	</td>
	
	
	<td id="rEsP72-chdextr" class="z-row-inner"><div id="rEsP72-cell" class="z-row-cnt z-overflow-hidden">
	<input id="cell_<%=row %>_16"
	row="<%=row%>" 
	update_id="${record.getId()}"
	col="16"
	style="width:70px;" 
	class="keylistener changes z-decimalbox z-decimalbox-inplace"
	value="<c:out value="${record.getRotbContS()}"/>" type="text" size="11">
	</div>
	</td>
	
	
	<td id="rEsP82-chdextr" class="z-row-inner"><div id="rEsP82-cell" class="z-row-cnt z-overflow-hidden">
	<input
	 id="cell_<%=row %>_17"
	 col="17"
	 row="<%=row%>" 
	 update_id="${record.getId()}"
	 style="width:70px;" 
	 class="keylistener changes z-decimalbox z-decimalbox-inplace" 
	 value="${record.getGrpDemandtdS()}" 
	 type="text" size="11">
	</div>
	</td>
	
	<td id="rEsP92-chdextr" class="z-row-inner">
	<div id="rEsP92-cell" class="z-row-cnt z-overflow-hidden">
	<input 
	id="cell_<%=row %>_18"
	col="18"
	update_id="${record.getId()}"
	row="<%=row%>" 
	style="width:70px;" class="changes keylistener z-decimalbox z-decimalbox-inplace" 
	value="<c:out value="${record.getGrpPricetdS()}"/>" 
	type="text" size="11">
	</div>
	</td>
	
	<td id="rEsPa2-chdextr" class="z-row-inner">
	<div id="rEsPa2-cell" class="z-row-cnt z-overflow-hidden">
	<span id="rEsPa2" class="z-label"></span></div></td>
</tr>
<%
row++;
%>	                                                                                    
                                                                                 </c:forEach> 
                                                                                 </tbody>
                                                                                 <tbody id="mXDPc-empty" class="z-grid-empty-body" style="display:none">
                                                                                    <tr>
                                                                                       <td colspan="1"></td>
                                                                                    </tr>
                                                                                 </tbody>
                                                                              </table>
                                                                           </div>
                                                                           <div id="mXDPc-frozen" class="z-grid-frozen">
                                                                              <div id="mXDPf" style="height: 0px; " class="z-frozen">
                                                                                 <div id="mXDPf-cave" class="z-frozen-body" style="height: 0px; width: 750px;">
                                                                                    <div id="mXDPg" style="padding: 0 10px;" class="z-div"></div>
                                                                                 </div>
                                                                                 <div id="mXDPf-scrollX" class="z-frozen-inner" tabindex="-1" style="display:none;">
                                                                                    <div style="height: 0px; width: 1089px;"></div>
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
</div>                                            
                                             </td>
                                          </tr>
                                       </tbody>
                                    </table>
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
      <iframe id="z_ddstkup" frameborder="no" tabindex="-1" src="" style="position: absolute; overflow: hidden; opacity: 0; display: none;"></iframe>
      <div style="left: -1000px; top: -1000px; position: absolute; visibility: hidden; border: none; white-space: nowrap; word-spacing: 0px; direction: ltr; text-overflow: clip; text-transform: none; text-shadow: none; text-indent: 0px; text-decoration: none; text-align: center; line-height: normal; letter-spacing: normal; font-style: normal; font-weight: bold; font-size: 11px; font-family: arial, sans-serif;">
         <div class="z-column-sort-img"></div>
         Comments
      </div>
   </body>
</html>