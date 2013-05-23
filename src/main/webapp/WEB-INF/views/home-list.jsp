<li>
    <div class="icon">
        <img src="http://uichange.com/UMS/files/${theme.iconPath}">
    </div>
    <div class="y-split"></div>
    <div class="info">
        <p class="title">${title}</p>
        <p class="txt">${shortDescription}</p>
        <div class="y-split right"></div>
        <div class="down-btn">
            <img src="${ctx}/static/images/2.0/down.png">
            <span><s:text name="home.down"></s:text></span>
        </div>
    </div>
    <a href="${ctx}/home!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
</li>