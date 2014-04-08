<%@page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::</title>
<%@include file="common/header.jsp"%>
<script type="text/javascript">
$(function(){
    $('#header .extension ul li a').click(function(){
      if( $(this).attr('href') == "/" ) {
        mainFrame.src = "/";
      } else {
    	var args = { URI:$(this).attr('href') };
    	PageNavigator.navigate(args);
      }
    });
});
</script>
</head>
<body>
    <div id="xe" class="hybrid">
        <div id="container" class="ece">
            <!-- Bottom 시작 -->
            <jsp:include page="common/top_menu.jsp"></jsp:include>
            <!-- Bottom 종료 -->
            <hr />
            <!-- Main 시작 -->
            <div id="body" style="height: 600px;">
                <div id="content">
                    <h2>Content</h2>
                    <div class="section">
                        <h3>W3C Develops Web Standards and Guidelines</h3>
                        <p>
                            <img src="img/c.gif" width="200" height="150" id="preview" class="preview" alt="Dynamic Layout Preview" />W3C
                            primarily pursues its mission through the creation of Web standards and guidelines. Since 1994, W3C has
                            published more than 110 such standards, called W3C Recommendations. W3C also engages in education and
                            outreach, develops software, and serves as an open forum for discussion about the Web. In order for the
                            Web to reach its full potential, the most fundamental Web technologies must be compatible with one
                            another and allow any hardware and software used to access the Web to work together. W3C refers to this
                            goal as “Web interoperability.” By publishing open (non-proprietary) standards for Web languages and
                            protocols, W3C seeks to avoid market fragmentation and thus Web fragmentation.
                        </p>
                        <p>Tim Berners-Lee and others created W3C as an industry consortium dedicated to building consensus
                            around Web technologies. Mr. Berners-Lee, who invented the World Wide Web in 1989 while working at the
                            European Organization for Nuclear Research (CERN), has served as the W3C Director since W3C was founded,
                            in 1994.</p>
                    </div>
                    <div class="section">
                        <ul class="layout">
                            <li><button type="button" onclick="fixed(); c()" onmouseover="img_c()" onfocus="img_c()">FIXED
                                    C</button></li>
                            <li><button type="button" onclick="liquid(); c()" onmouseover="img_c()" onfocus="img_c()">LIQUID
                                    C</button></li>
                            <li><button type="button" onclick="hybrid(); c()" onmouseover="img_c()" onfocus="img_c()">HYBRID
                                    C</button></li>
                            <li><button type="button" onclick="fixed(); ce()" onmouseover="img_ce()" onfocus="img_ce()">FIXED
                                    CE</button></li>
                            <li><button type="button" onclick="fixed(); ec()" onmouseover="img_ec()" onfocus="img_ec()">FIXED
                                    EC</button></li>
                            <li><button type="button" onclick="liquid(); ce()" onmouseover="img_ce()" onfocus="img_ce()">LIQUID
                                    CE</button></li>
                            <li><button type="button" onclick="liquid(); ec()" onmouseover="img_ec()" onfocus="img_ec()">LIQUID
                                    EC</button></li>
                            <li><button type="button" onclick="hybrid(); ce()" onmouseover="img_ce()" onfocus="img_ce()">HYBRID
                                    CE</button></li>
                            <li><button type="button" onclick="hybrid(); ec()" onmouseover="img_ec()" onfocus="img_ec()">HYBRID
                                    EC</button></li>
                            <li><button type="button" onclick="fixed(); cee()" onmouseover="img_cee()" onfocus="img_cee()">FIXED
                                    CEE</button></li>
                            <li><button type="button" onclick="fixed(); ece()" onmouseover="img_ece()" onfocus="img_ece()">FIXED
                                    ECE</button></li>
                            <li><button type="button" onclick="fixed(); eec()" onmouseover="img_eec()" onfocus="img_eec()">FIXED
                                    EEC</button></li>
                            <li><button type="button" onclick="liquid(); cee()" onmouseover="img_cee()" onfocus="img_cee()">LIQUID
                                    CEE</button></li>
                            <li><button type="button" onclick="liquid(); ece()" onmouseover="img_ece()" onfocus="img_ece()">LIQUID
                                    ECE</button></li>
                            <li><button type="button" onclick="liquid(); eec()" onmouseover="img_eec()" onfocus="img_eec()">LIQUID
                                    EEC</button></li>
                            <li><button type="button" onclick="hybrid(); cee()" onmouseover="img_cee()" onfocus="img_cee()">HYBRID
                                    CEE</button></li>
                            <li><button type="button" onclick="hybrid(); ece()" onmouseover="img_ece()" onfocus="img_ece()">HYBRID
                                    ECE</button></li>
                            <li><button type="button" onclick="hybrid(); eec()" onmouseover="img_eec()" onfocus="img_eec()">HYBRID
                                    EEC</button></li>
                        </ul>
                    </div>
                </div>
                <hr />
                <div class="extension e1">
                    <div class="section">
                        <h2>Extension 1</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                    <div class="section">
                        <h2>Extension 1</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                    <div class="section">
                        <h2>Extension 1</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                </div>
                <hr />
                <div class="extension e2">
                    <div class="section">
                        <h2>Extension 2</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                    <div class="section">
                        <h2>Extension 2</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                    <div class="section">
                        <h2>Extension 2</h2>
                        <ul>
                            <li>About W3C</li>
                            <li>About W3C Membership</li>
                            <li>W3C Supporters Program</li>
                            <li>Introduction to Web Site</li>
                            <li>Search, Site Index, Keywords</li>
                            <li>Mail Archive Search</li>
                            <li>FAQs</li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- Main 종료 -->
            <hr />
            <!-- Bottom 시작 -->
            <jsp:include page="common/footer.jsp"></jsp:include>
            <!-- Bottom 종료 -->
        </div>
    </div>
</body>
</html>