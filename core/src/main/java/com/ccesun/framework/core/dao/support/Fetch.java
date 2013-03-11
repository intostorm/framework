package com.ccesun.framework.core.dao.support;

public class Fetch {

	public static enum JoinType {
		JOINTYPE_INNER, JOINTYPE_LEFT, JOINTYPE_RIGHT,
		JOINTYPE_INNER_FETCH, JOINTYPE_LEFT_FETCH, JOINTYPE_RIGHT_FETCH
	}

    private String targetObjectName;

    private JoinType joinType;

    private Fetch(String targetObjectName, JoinType joinType) {
        this.targetObjectName = targetObjectName;
        this.joinType = joinType;
    }
    
    public static Fetch innerFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_INNER_FETCH);
    }
    
    public static Fetch leftFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_LEFT_FETCH);
    }

    public static Fetch rightFetch(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_RIGHT_FETCH);
    }
    
    public static Fetch innerJoin(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_INNER);
    }
    
    public static Fetch leftJoin(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_LEFT);
    }

    public static Fetch rightJoin(String targetObjectName) {
        return new Fetch(targetObjectName, JoinType.JOINTYPE_RIGHT);
    }
    
	public String getTargetObjectName() {
		return targetObjectName;
	}

	public void setTargetObjectName(String targetObjectName) {
		this.targetObjectName = targetObjectName;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
   
}
