<#macro bind path>
    <#assign status = spring.status(path)>
    <#nested status>
</#macro>

<#macro bindEscaped path>
    <#assign status = spring.status(path, true)>
    <#nested status>
</#macro>

<#macro formInput path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <input type="text" value="${spring.status(path).value?html}" ${attributes} />
</#macro>

<#macro formTextarea path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <textarea ${attributes}>${spring.status(path).value?html}</textarea>
</#macro>

<#macro formHiddenInput path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <input type="hidden" value="${spring.status(path).value?html}" ${attributes} />
</#macro>

<#macro formPasswordInput path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <input type="password" value="${spring.status(path).value?html}" ${attributes} />
</#macro>

<#macro formSingleSelect path options attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <select ${attributes}>
        <#list options as option>
            <option value="${option.value}"<#if spring.status(path).actualValue == option.value> selected</#if>>${option.label}</option>
        </#list>
    </select>
</#macro>

<#macro formMultiSelect path options attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <select multiple="multiple" ${attributes}>
        <#list options as option>
            <option value="${option.value}"<#if spring.status(path).actualValue?seq_contains(option.value)> selected</#if>>${option.label}</option>
        </#list>
    </select>
</#macro>

<#macro formRadioButtons path options separator attributes="">
    <#list options as option>
        <input type="radio" id="${spring.status(path).expression}${option_index}" name="${spring.status(path).expression}" value="${option.value}"<#if spring.status(path).actualValue == option.value> checked</#if> ${attributes} />
        <label for="${spring.status(path).expression}${option_index}">${option.label}</label>
        ${separator}
    </#list>
</#macro>

<#macro formCheckbox path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '" name="' + spring.status(path).expression + '"'>
    <input type="checkbox" value="true" <#if spring.status(path).actualValue == true> checked</#if> ${attributes} />
</#macro>

<#macro formCheckboxes path options separator attributes="">
    <#list options as option>
        <input type="checkbox" id="${spring.status(path).expression}${option_index}" name="${spring.status(path).expression}" value="${option.value}"<#if spring.status(path).actualValue?seq_contains(option.value)> checked</#if> ${attributes} />
        <label for="${spring.status(path).expression}${option_index}">${option.label}</label>
        ${separator}
    </#list>
</#macro>

<#macro formErrors path attributes="">
    <#assign attributes = attributes + ' id="' + spring.status(path).expression + '.errors" class="errors"'>
    <#if spring.status(path).error>
        <span ${attributes}>
			<#list spring.status(path).errorMessages as error>
                ${error}<br>
            </#list>
		</span>
    </#if>
</#macro>

<#if RequestContext.hasOwnProperty("springMacroRequestContext")>
    <#assign spring=RequestContext.springMacroRequestContext>
<#else>
    <#assign spring=RequestContext>
</#if>