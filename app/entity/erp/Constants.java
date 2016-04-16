package entity.erp;

/**
 * Created by Sunny Wu on 16/1/6.
 * ERP系统登录信息
 */

	public abstract class Constants {

	public static final String URL = play.Play.application().configuration().getString("erp.url");

	public static final String COMPANY = play.Play.application().configuration().getString("erp.company");

	public static final String LOGIN_NAME = play.Play.application().configuration().getString("erp.login.name");

	public static final String PASSWORD = play.Play.application().configuration().getString("erp.login.pwd");

	public static final String SECRET = play.Play.application().configuration().getString("erp.secret");

}
