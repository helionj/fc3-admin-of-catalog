package com.helion.admin.catalog.application.castmember.create;

import com.helion.admin.catalog.domain.castmember.CastMemberType;

public record CreateCastMemberCommand(String name, CastMemberType type) {

    public static CreateCastMemberCommand with(final String aName, final CastMemberType aType){
        return new CreateCastMemberCommand(aName, aType);
    }
}
