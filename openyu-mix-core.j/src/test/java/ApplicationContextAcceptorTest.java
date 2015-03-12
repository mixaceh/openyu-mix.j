import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.socklet.acceptor.service.AcceptorService;

public class ApplicationContextAcceptorTest extends BaseTestSupporter
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",// 
				"META-INF/applicationContext-sfc.xml",//				
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				"applicationContext-schedule.xml",//
				"META-INF/applicationContext-sls.xml",//
				"applicationContext-acceptor.xml",//
		});
	}

	@Test
	public void accountAcceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("accountAcceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void loginAcceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("loginAcceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void masterAcceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("masterAcceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void slave1Acceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("slave1Acceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void slave2Acceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("slave2Acceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void slave3Acceptor()
	{
		AcceptorService bean = (AcceptorService) applicationContext.getBean("slave3Acceptor");
		System.out.println(bean);
		assertNotNull(bean);
	}
}
