<#assign itemCount = 0/>
<#assign columnCount = 3/>
<#if parameters.list??>

    <@s.iterator value="parameters.list">
        <#if parameters.listKey??>
            <#assign itemKey = stack.findValue(parameters.listKey)/>
        <#else>
            <#assign itemKey = stack.findValue('top')/>
        </#if>
        <#if parameters.listValue??>
            <#assign itemValue = stack.findString(parameters.listValue)?default("")/>
        <#else>
            <#assign itemValue = stack.findString('top')/>
        </#if>
        <#if (itemCount%columnCount) == 0>

        </#if>
<#assign itemKeyStr=itemKey.toString() />
	<label for="${parameters.name?html}-${itemKeyStr?html}" class="checkbox inline">
	<input type="checkbox" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}-${itemKeyStr?html}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey)>
 checked="checked"<#rt/>
        </#if>
        <#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
        </#if>
        <#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
        </#if>
        <#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
        <#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/><#rt/>
	${itemValue?html}</label></td>
	<#assign itemCount = itemCount + 1/>
    <#if (itemCount%columnCount) == 0>

	</#if>
    </@s.iterator>
    <#if (itemCount%columnCount) != 0>

	</#if>

<#else>
  &nbsp;
</#if>