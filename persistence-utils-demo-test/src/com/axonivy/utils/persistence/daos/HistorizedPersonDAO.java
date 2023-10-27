package com.axonivy.utils.persistence.daos;

import com.axonivy.utils.persistence.dao.AuditableIdDAO;
import com.axonivy.utils.persistence.entities.HistorizedPerson;
import com.axonivy.utils.persistence.entities.HistorizedPerson_;


public class HistorizedPersonDAO extends AuditableIdDAO<HistorizedPerson_, HistorizedPerson> implements BaseDAO {

	private static final HistorizedPersonDAO instance = new HistorizedPersonDAO();

	private HistorizedPersonDAO() {
	}

	public static HistorizedPersonDAO getInstance() {
		return instance;
	}

	@Override
	protected Class<HistorizedPerson> getType() {
		return HistorizedPerson.class;
	}

}