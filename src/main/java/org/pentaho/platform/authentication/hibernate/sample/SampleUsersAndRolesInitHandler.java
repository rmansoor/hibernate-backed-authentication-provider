/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2007 - 2009 Pentaho Corporation.  All rights reserved.
 *
*/
package org.pentaho.platform.authentication.hibernate.sample;

import org.pentaho.platform.authentication.hibernate.IRole;
import org.pentaho.platform.authentication.hibernate.IUser;
import org.pentaho.platform.authentication.hibernate.IUserRoleDao;
import org.pentaho.platform.authentication.hibernate.CustomRole;
import org.pentaho.platform.authentication.hibernate.CustomUser;
import org.pentaho.platform.authentication.hibernate.UncategorizedUserRoleDaoException;
import org.pentaho.platform.authentication.hibernate.HibernateUserRoleDao.InitHandler;
import org.pentaho.platform.authentication.hibernate.messages.Messages;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Inserts sample users and roles into tables if those tables are empty.
 * 
 * <p>This handler checks to see if the users table is empty. If it is empty, then it inserts sample users and roles.
 * </p>
 * 
 * TODO mlowery Use DefaultPentahoPasswordEncoder to encode the hard-coded passwords.
 * 
 * @see InitHandler
 * @author mlowery
 */
public class SampleUsersAndRolesInitHandler extends HibernateDaoSupport implements InitHandler {

  // ~ Static fields/initializers ====================================================================================== 

  // ~ Instance fields =================================================================================================

  private IUserRoleDao userRoleDao;

  // ~ Constructors ====================================================================================================

  public SampleUsersAndRolesInitHandler() {
    super();
  }

  // ~ Methods =========================================================================================================

  public void handleInit() {

    try {
      boolean databaseEmpty = userRoleDao.getUsers().isEmpty();

      if (!databaseEmpty) {
    	  cleanup();
      }
        CustomRole adminRole = new CustomRole("Admin", "Super"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole administratorRole = new CustomRole("Administrator", "Admininstrator"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole ceo = new CustomRole("ceo", "Chief Executive Officer"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole cto = new CustomRole("cto", "Chief Technology Officer"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole dev = new CustomRole("dev", "Developer"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole devMgr = new CustomRole("devmgr", "Development Manager"); //$NON-NLS-1$ //$NON-NLS-2$
        CustomRole is = new CustomRole("is", "Information Services"); //$NON-NLS-1$ //$NON-NLS-2$

        userRoleDao.createRole(adminRole);
        userRoleDao.createRole(administratorRole);
        userRoleDao.createRole(ceo);
        userRoleDao.createRole(cto);
        userRoleDao.createRole(dev);
        userRoleDao.createRole(devMgr);
        userRoleDao.createRole(is);

        CustomUser admin = new CustomUser("joe", "cGFzc3dvcmQ=", null, true); //$NON-NLS-1$ //$NON-NLS-2$
        admin.addRole(adminRole);
        CustomUser jim = new CustomUser("jim", "cGFzc3dvcmQ=", null, true); //$NON-NLS-1$ //$NON-NLS-2$
        jim.addRole(administratorRole);
        jim.addRole(ceo);
        CustomUser john = new CustomUser("john", "cGFzc3dvcmQ=", null, true); //$NON-NLS-1$ //$NON-NLS-2$
        john.addRole(dev);
        CustomUser susan = new CustomUser("susan", "cGFzc3dvcmQ=", null, true); //$NON-NLS-1$ //$NON-NLS-2$
        susan.addRole(cto);
        susan.addRole(is);
        CustomUser sally = new CustomUser("sally", "cGFzc3dvcmQ=", null, true); //$NON-NLS-1$ //$NON-NLS-2$
        sally.addRole(dev);
        sally.addRole(devMgr);

        userRoleDao.createUser(admin);
        userRoleDao.createUser(jim);
        userRoleDao.createUser(john);
        userRoleDao.createUser(susan);
        userRoleDao.createUser(sally);
    } catch (UncategorizedUserRoleDaoException e) {
      // log error and simply return
      logger.error(Messages.getInstance().getString("SampleUsersAndRolesInitHandler.ERROR_0001_COULD_NOT_INSERT_SAMPLES"), e); //$NON-NLS-1$
    }

  }

  public void setUserRoleDao(final IUserRoleDao userRoleDao) {
    this.userRoleDao = userRoleDao;
  }
  
  private void cleanup ( ) {
	  for(IUser user:userRoleDao.getUsers()) {
		  userRoleDao.deleteUser(user);
	  }
	  for(IRole role:userRoleDao.getRoles()) {
		  userRoleDao.deleteRole(role);
	  }
  }

}
