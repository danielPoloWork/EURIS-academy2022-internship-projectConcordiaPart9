package com.euris.academy2022.concordia.utils.constants;

import com.euris.academy2022.concordia.utils.enums.MemberRole;

public interface SecurityConstant {

    String MAPPING_MEMBER = "/api/member";
    String MAPPING_USER_DETAIL_MANAGER = "/api/userDetailManager";
    String MAPPING_TASK = "/api/task";
    String MAPPING_ASSIGNEE = "/api/assignee";
    String MAPPING_COMMENT = "/api/comment";
    String MAPPING_TABLET= "/api/tablet";
    String MAPPING_CONFIGURATION = "/api/configuration";
    String MAPPING_CONNECTION_WINDOW = "/api/connectionWindow";

    String BEAN_ADMIN = "beanUdmAdmin";
    String BEAN_USERNAME_ADMIN = "beanUdmUsernameAdmin";
    String BEAN_PASSWORD_ADMIN = "beanUdmPasswordAdmin";

    String BEAN_BASIC_MEMBER = "beanUdmBasicMember";
    String BEAN_USERNAME_BASIC_MEMBER = "beanUdmUsernameBasicMember";
    String BEAN_PASSWORD_BASIC_MEMBER = "beanUdmPasswordBasicMember";

    String A1 = MemberRole.A1.getLabel();
    String A2 = MemberRole.A2.getLabel();
    String A3 = MemberRole.A3.getLabel();
    String B1 = MemberRole.B1.getLabel();
    String C1 = MemberRole.C1.getLabel();
    String C2 = MemberRole.C2.getLabel();
    String CONCORDIA = MemberRole.CONCORDIA.getLabel();
    String ADMIN = MemberRole.ADMIN.getLabel();
    String MANAGER = MemberRole.MANAGER.getLabel();
}
