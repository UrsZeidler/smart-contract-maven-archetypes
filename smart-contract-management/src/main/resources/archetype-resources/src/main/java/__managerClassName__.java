#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package};

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.adridadou.ethereum.EthereumFacade;
import org.adridadou.ethereum.keystore.FileSecureKey;
import org.adridadou.ethereum.keystore.SecureKey;
import org.adridadou.ethereum.values.EthAccount;
import org.adridadou.ethereum.values.EthValue;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


/**
 * @author 
 *
 */
public class ${managerClassName} {

	private EthereumFacade ethereum;
//	private ContractDeployer deployer;
	private long millis;
	private EthAccount sender;

	private interface DoAndWaitOneTime<T> {
		boolean isDone();

		CompletableFuture<T> doIt();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Options options = createOptions();
		CommandLineParser parser = new DefaultParser();
		int returnValue = 0;
		try {
			CommandLine commandLine = parser.parse(options, args);
			if (commandLine.hasOption("h")) {
				printHelp(options);
				return;
			}

			String managerAddress = null;
			String senderKey = null;
			String senderPass = null;
			if (commandLine.hasOption("sk"))
				senderKey = commandLine.getOptionValue("sk");
			if (commandLine.hasOption("sp"))
				senderPass = commandLine.getOptionValue("sp");

			${managerClassName} manager = new ${managerClassName}();

			try {
				manager.init(senderKey, senderPass, managerAddress);
				if (commandLine.hasOption("f")) {
					String[] values = commandLine.getOptionValues("f");

					String filename = values[0];
					String isCompiled = values[1]; 
					//manager.deployer.setContractSource(filename, Boolean.parseBoolean(isCompiled));
				}
				if(commandLine.hasOption("millis")){
					manager.setMillis(Long.parseLong(commandLine.getOptionValue("millis")));
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				printHelp(options);
				returnValue = 10;
			}

		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
			printHelp(options);
			returnValue = 10;
		}

		System.exit(returnValue);
	}

	private void setManager(String cdatabaseAddress) throws IOException, InterruptedException, ExecutionException {
//		manager = new DeployDuo<ChecksumDatabase>(EthAddress.of(cdatabaseAddress), null);
//		manager.contractInstance = deployer.createChecksumDatabaseProxy(sender, manager.contractAddress);
	}

	private void init(String senderKey, String senderPass, String managerAddress) throws Exception {
//		ethereum = EthereumInstance.getInstance().getEthereum();
//		String property = System.getProperty("EthereumFacadeProvider");
//		// testnetProvider
//		if (property != null && (property.equalsIgnoreCase("rpc") || property.equalsIgnoreCase("ropsten")
//				|| property.equalsIgnoreCase("InfuraRopsten"))) {
//
//			millis = 2000L;
//		} else if (property != null && property.equalsIgnoreCase("private")) {
//			sender = new EthAccount(ECKey.fromPrivate(BigInteger.valueOf(100000L)));
//			millis = 100L;
//		} else {
//			sender = new EthAccount(
//					ECKey.fromPrivate(Hex.decode("3ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c")));
//			millis = 10L;
//		}
//
//		if (senderKey != null && !senderKey.isEmpty() && sender == null) {
//			sender = unlockAccount(senderKey, senderPass);
//		}
//		deployer = new ContractDeployer(ethereum,"/combined.json",true);
//		if (managerAddress != null) {
//			setManager(managerAddress);
//		}
	}

	/**
	 * Unlocks an account.
	 * 
	 * @param pathname
	 *            the key file
	 * @param pass
	 *            the pass to unlocl
	 * @return the account
	 * @throws Exception
	 */
	private EthAccount unlockAccount(String pathname, String pass) throws Exception {
		SecureKey key2 = new FileSecureKey(new File(pathname));
		System.out.println("unlock key: " + pathname);
		EthAccount decode = key2.decode(pass);
		String senderAddressS = decode.getAddress().withLeading0x();
		EthValue balance = ethereum.getBalance(decode);
		System.out.println("Sender address and amount:" + senderAddressS + "->" + balance);
		return decode;
	}

	private void doAndWait(String message, DoAndWaitOneTime<?> action) throws InterruptedException, ExecutionException {
		System.out.println(message);
		doAndWait(action);
	}

	private void doAndWait(DoAndWaitOneTime<?> action) throws InterruptedException, ExecutionException {
		int timeOut = 0;
		if (!action.isDone()) {
			action.doIt().get();
			while (!action.isDone() && timeOut++ < 200)
				synchronized (this) {
					System.out.print(".");
					wait(millis);
				}
		}
		System.out.println();
		if (timeOut >= 200)
			System.out.println("Timeout:" + action);
	}

	private static Options createOptions() {
		Options options = new Options();

		// OptionGroup keyOptionGroup = new OptionGroup();
		// keyOptionGroup.setRequired(false);
		options.addOption(Option//
				.builder("f")//
				.desc("Set the contract source or the compiled json.")//
				.longOpt("file")//
				.hasArg()//
				.argName("file alreadyCompiled").numberOfArgs(2).build());

		options.addOption(Option//
				.builder("sk")//
				.desc("Set the sender key file.")//
				.longOpt("senderKey")//
				.hasArg()//
				.argName("keyFile")//
				.numberOfArgs(1).build());
		options.addOption(Option//
				.builder("sp")//
				.desc("Set the pass of the key of the sender.")//
				.longOpt("senderPass")//
				.hasArg()//
				.argName("password")
				.numberOfArgs(1).build());
		options.addOption(Option//
				.builder("millis")//
				.desc("The millisec to wait between checking the action.")//
				.hasArg()//
				.argName("millisec").numberOfArgs(1).build());

		OptionGroup helpOptionGroup = new OptionGroup();
		helpOptionGroup.addOption(Option.builder("h")//
				.desc("show help and usage")//
				.hasArg(false).build());

		OptionGroup actionOptionGroup = new OptionGroup();
		actionOptionGroup.setRequired(true);
		//TODO: add the commandline actions
		
		
		options.addOptionGroup(actionOptionGroup);
		// options.addOptionGroup(keyOptionGroup);
		options.addOptionGroup(helpOptionGroup);
		return options;
	}

	/**
	 * @param options
	 */
	private static void printHelp(Options options) {
		System.out.println("used EthereumFacadeProvider:" + System.getProperty("EthereumFacadeProvider") + "${symbol_escape}n${symbol_escape}n");

		StringBuffer buffer = new StringBuffer();
		buffer.append("change the ethereum client via -DEthereumFacadeProvider=<type>${symbol_escape}n")//
				.append("type : main - the main net${symbol_escape}n")//
				.append("       test - the test net${symbol_escape}n")//
				.append("       ropsten - the ropsten net${symbol_escape}n")//
				.append("       private - the private net${symbol_escape}n")//
				.append("       InfuraRopsten - the ropsten net via Infrua${symbol_escape}n")//
				.append("       InfuraMain - the main net via Infrua${symbol_escape}n")//
				.append("           -DapiKey=<key> - the the api key for the service${symbol_escape}n")//
				.append("       rpc - connect via rpc${symbol_escape}n")//
				.append("          -Drpc-url=<url> - the url of the rpc server${symbol_escape}n")//
				.append("          -Dchain-id=<id> - the chain id (0 for the main chain and 3 for ropsten)${symbol_escape}n")//
				.append("${symbol_escape}n");

		HelpFormatter formatter = new HelpFormatter();
		String header = "${symbol_escape}nA deployer and manager for for a version database on the blockchain. (c) Urs Zeidler 2017n";
		String footer = "${symbol_escape}nexample: ${symbol_escape}n${symbol_escape}n" + buffer.toString();
		formatter.printHelp(150, "checksum database on the blockchain", header, options, footer, true);
	}

	public void setMillis(long millis) {
		this.millis = millis;
	}

}
