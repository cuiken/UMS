<li>
    <div class="icon">
        <img src="${ctx}/files/${theme.iconPath}">
    </div>
    <div class="y-split"></div>
    <div class="info">
        <p class="title">${title}</p>
        <p class="txt">${shortDescription}</p>
    </div>
    <s:if test="theme.ishot==1">
        <span class="icon_n"></span>
    </s:if><s:elseif test="theme.isnew==1">
    <span class="icon_n icon_n_new"></span>
</s:elseif>

    <a href="${ctx}/store/mpb-store!details.action?id=${theme.id}&${queryString}" class="down-area"></a>
</li>