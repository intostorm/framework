<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE tiles-definitions PUBLIC "-//Apache Software Foundation//DTD Tiles Configuration 2.1//EN" "http://tiles.apache.org/dtds/tiles-config_2_1.dtd">
<tiles-definitions>
    <definition extends="default" name="${materialDetail.material.domainName?uncap_first}/list">
        <put-attribute name="body" value="/WEB-INF/views/${materialDetail.material.domainName?lower_case}/${materialDetail.material.domainName?lower_case}_list.jsp" />
    </definition>
    <definition extends="default" name="${materialDetail.material.domainName?uncap_first}/show">
        <put-attribute name="body" value="/WEB-INF/views/${materialDetail.material.domainName?lower_case}/${materialDetail.material.domainName?lower_case}_show.jsp" />
    </definition>
    <definition extends="default" name="${materialDetail.material.domainName?uncap_first}/update">
        <put-attribute name="body" value="/WEB-INF/views/${materialDetail.material.domainName?lower_case}/${materialDetail.material.domainName?lower_case}_edit.jsp" />
    </definition>
    <definition extends="default" name="${materialDetail.material.domainName?uncap_first}/create">
        <put-attribute name="body" value="/WEB-INF/views/${materialDetail.material.domainName?lower_case}/${materialDetail.material.domainName?lower_case}_edit.jsp" />
    </definition>
</tiles-definitions>
