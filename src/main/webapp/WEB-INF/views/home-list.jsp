<li>
    <div class="icon">
        <img src="${ctx}/image.action?path=${theme.iconPath}">
    </div>
    <div class="y-split"></div>
    <div class="info">
        <p class="title">${title}</p>
        <p class="txt">${shortDescription}</p>
        <%--<div class="y-split right"></div>--%>
        <%--<div class="down-btn">--%>
            <%--<a href="#" onclick="goDownload('${theme.id}','${theme.downloadURL}');">--%>
                <%--<img src="${ctx}/static/images/2.0/down.png">--%>
            <%--<span><s:text name="home.down"></s:text></span>--%>
            <%--</a>--%>
        <%--</div>--%>
    </div>
    <s:if test="theme.ishot==1">
        <span class="icon_n"></span>
    </s:if><s:elseif test="theme.isnew==1">
        <span class="icon_n icon_n_new"></span>
    </s:elseif>

    <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
</li>