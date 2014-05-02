package eu.deustotech.clips;


public class Environment {
	private static final String CLIPSJNI_VERSION = "0.1";

	public static final String FACTS = "facts";
	public static final String RULES = "rules";
	public static final String DEFFUNCTIONS = "deffunctions";
	public static final String COMPILATIONS = "compilations";
	public static final String INSTANCES = "instances";
	public static final String SLOTS = "slots";
	public static final String ACTIVATIONS = "activations";
	public static final String STATISTICS = "statistics";
	public static final String FOCUS = "focus";
	public static final String GENERIC_FUNCTIONS = "generic-functions";
	public static final String METHODS = "methods";
	public static final String GLOBALS = "globals";
	public static final String MESSAGES = "messages";
	public static final String MESSAGE_HANDLERS = "message-handlers";

	static {
		System.loadLibrary("clips");
		System.loadLibrary("clipsdroid");
	}

	private long theEnvironment;

	/****************/
	/* Environment: */
	/****************/
	public Environment() {
		super();
		theEnvironment = createEnvironment();
	}

	/**
	 * @return
	 * 	CLIPSJNI's version number.
	 */
	public static String getCLIPSJNIVersion() {
		return CLIPSJNI_VERSION;
	}

	/***************************************************/
	/* getCLIPSVersion: Gets the CLIPS version number. */
	/***************************************************/
	public static native String getCLIPSVersion();

	/**
	 * @return
	 * 	String representation of CLIPSJNI's and CLIPS' version numbers.
	 */
	public static String getVersion() {
		return "CLIPSJNI version " + getCLIPSJNIVersion() + " (CLIPS version "
				+ getCLIPSVersion() + ")";
	}

	/**************************/
	/* getEnvironmentAddress: */
	/**************************/
	public long getEnvironmentAddress() {
		return theEnvironment;
	}

	/***************************/
	/* createCLIPSEnvironment: */
	/***************************/
	private native long createEnvironment();

	/****************************************/
	/* clear: Clears the CLIPS environment. */
	/****************************************/
	private native void clear(long env);

	/**
	 * Clears the CLIPS environment.
	 * 
	 * Removes all constructs and all associated data structures (such as facts and instances) from the CLIPS environment.
	 * A clear may be performed safely at any time, however, certain constructs will not allow themselves to be deleted while they are in use.
	 * For example, while deffacts are being reset (by the reset command), it is not possible to remove them using the clear command.
	 * Note that the clear command does not effect many environment characteristics (such as the current conflict resolution strategy).
	 */
	public void clear() {
		clear(theEnvironment);
	}

	/****************************************/
	/* reset: Resets the CLIPS environment. */
	/****************************************/
	private native void reset(long env);

	/**
	 * Reset:
	 * 	1. remove all activated rules from agenda
	 *  2. remove all facts from the fact-list
	 *  3. assert the facts from existing deffacts
	 */
	public void reset() {
		reset(theEnvironment);
	}

	/*********/
	/* load: */
	/*********/
	private native void load(long env, String filename);

	/**
	 * Loads the constructs stored in the file specified by <i>filename</i> into the environment.
	 * @param filename
	 *	The file which stores the constructs to be loaded.
	 * @throws CLIPSError
	 *	CLIPS throws an error when it detects any problem.
	 *	For example, it warns when the syntax of the constructs written into the file is incorrect.
	 */
	public void load(String filename) throws CLIPSError {
		load(theEnvironment, filename);
	}

	/**************/
	/* loadFacts: */
	/**************/
	private native boolean loadFacts(long env, String filename);

	/**************/
	/* loadFacts: */
	/**************/
	public boolean loadFacts(String filename) {
		return loadFacts(theEnvironment, filename);
	}

	/**********/
	/* watch: */
	/**********/
	private native boolean watch(long env, String watchItem);

	/**
	 * If an item is being watched, then an informational message will be displayed each time a rule is activated or deactivated.
	 * @param watchItem
	 * @return
	 */
	public boolean watch(String watchItem) {
		return watch(theEnvironment, watchItem);
	}

	/************/
	/* unwatch: */
	/************/
	private native boolean unwatch(long env, String watchItem);

	/**
	 * This function disables the effect of the watch command.
	 * @param watchItem
	 * @return
	 */
	public boolean unwatch(String watchItem) {
		return unwatch(theEnvironment, watchItem);
	}

	/********/
	/* run: */
	/********/
	private native long run(long env, long runLimit);

	/**
	 * Starts execution of the rules.
	 * <ul>
	 * 	<li>If the focus stack is empty, then the MAIN module is automatically becomes the current focus.
	 *  The run command has no additional effect if evaluated while rules are executing. Note that the number of rules fired and timing information is no longer printed after the completion of the run command unless the statistics item is being watched.</li>
	 * 	<li>If the rules item is being watched, then an informational message will be printed each time a rule is fired.</li>
	 * </ul>
	 * 
	 * @param runLimit
	 * 	The execu­tion will cease after the specified number of rule firings or when the agenda con­tains no rule activations.
	 * 	If this argument is a negative integer, execution will cease when the agenda contains no rule activations.
	 * 	That is, passing a negative runLimit is equivalent to calling run method without arguments.
	 * 
	 * @return
	 */
	public long run(long runLimit) {
		return run(theEnvironment, runLimit);
	}

	/**
	 * Starts execution of the rules.
	 * <ul>
	 * 	<li>If the focus stack is empty, then the MAIN module is automatically becomes the current focus.
	 *  The run command has no additional effect if evaluated while rules are executing. Note that the number of rules fired and timing information is no longer printed after the completion of the run command unless the statistics item is being watched.</li>
	 * 	<li>If the rules item is being watched, then an informational message will be printed each time a rule is fired.</li>
	 * </ul>
	 * 
	 * @return
	 */
	public long run() {
		return run(theEnvironment, -1);
	}

	/*********/
	/* eval: */
	/*********/
	private native PrimitiveValue eval(long env, String evalStr);
	
	/**
	 * The eval function evaluates the string as though it were entered at the command prompt.
	 * 
	 * @param evalStr
	 * 		The string to be evaluated.
	 *		<b>NOTE:</b> eval does not permit the use of local variables (except when the local variables are defined as part of the command such as with an instance query function), nor will it evaluate any of the construct definition forms (i.e., defrule, deffacts, etc.).
	 *		The return value is the result of the evaluation of the string (or FALSE if an error occurs).
	 * @return
	 * 		A value with the result of the evaluation.
	 * @throws CLIPSError
	 * 		CLIPS throws an error when it detects any problem.
	 *		For example, it warns when evalStr's syntax is incorrect.
	 */
	public PrimitiveValue eval(String evalStr) throws CLIPSError {
		return eval(theEnvironment, evalStr);
	}

	/**********/
	/* build: */
	/**********/
	private native boolean build(long env, String buildStr);

	/**
	 * The build function evaluates the string as though it were entered at the command prompt.
	 * @param buildStr
	 * 		The construct to be added. That is, defrule, deffacts, etc.
	 * @return
	 * 		True if the construct was added (or false if an error occurs).
	 * @throws CLIPSError
	 * 		CLIPS throws an error when it detects any problem.
	 * 		For example, it warns when buildStr's syntax is incorrect.
	 */
	public boolean build(String buildStr) throws CLIPSError {
		return build(theEnvironment, buildStr);
	}

	/*****************/
	/* assertString: */
	/*****************/
	private native FactAddressValue assertString(long env, String factStr);

	/**
	 * It adds facts to the fact‑list using the assert.
	 * @param factStr
	 * 		The fact to be added.
	 * 		If a fact is asserted into the fact‑list that exactly matches an already existing fact, the new assertion will be ignored (this default behavior can be changed).
	 * @return
	 * 		The address of the newly added fact.
	 * 
	 * @throws CLIPSError
	 * 		CLIPS throws an error when it detects any problem.
	 * 		For example, it warns when factStr syntax is incorrect.
	 */
	public FactAddressValue assertString(String factStr) throws CLIPSError {
		return assertString(theEnvironment, factStr);
	}

	/**************/
	/* factIndex: */
	/**************/
	private static native long factIndex(Environment javaEnv, long env,
			long fact);

	/**************/
	/* factIndex: */
	/**************/
	public static long factIndex(FactAddressValue theFact) {
		return factIndex(theFact.getEnvironment(), theFact.getEnvironment()
				.getEnvironmentAddress(), theFact.getFactAddress());
	}

	/****************/
	/* getFactSlot: */
	/****************/
	private static native PrimitiveValue getFactSlot(Environment javaEnv,
			long env, long fact, String slotName);

	/****************/
	/* getFactSlot: */
	/****************/
	public static PrimitiveValue getFactSlot(FactAddressValue theFact,
			String slotName) {
		return getFactSlot(theFact.getEnvironment(), theFact.getEnvironment()
				.getEnvironmentAddress(), theFact.getFactAddress(), slotName);
	}

	/*****************/
	/* makeInstance: */
	/*****************/
	private native InstanceAddressValue makeInstance(long env,
			String instanceStr);

	/*****************/
	/* makeInstance: */
	/*****************/
	public InstanceAddressValue makeInstance(String instanceStr) {
		return makeInstance(theEnvironment, instanceStr);
	}

	/********************/
	/* getInstanceName: */
	/********************/
	private static native String getInstanceName(Environment javaEnv, long env,
			long instance);

	/********************/
	/* getInstanceName: */
	/********************/
	public static String getInstanceName(InstanceAddressValue theInstance) {
		return getInstanceName(theInstance.getEnvironment(), theInstance
				.getEnvironment().getEnvironmentAddress(),
				theInstance.getInstanceAddress());
	}

	/******************/
	/* directGetSlot: */
	/******************/
	private static native PrimitiveValue directGetSlot(Environment javaEnv,
			long env, long instance, String slotName);

	/******************/
	/* directGetSlot: */
	/******************/
	public static PrimitiveValue directGetSlot(
			InstanceAddressValue theInstance, String slotName) {
		return directGetSlot(theInstance.getEnvironment(), theInstance
				.getEnvironment().getEnvironmentAddress(),
				theInstance.getInstanceAddress(), slotName);
	}

	/***********************/
	/* destroyEnvironment: */
	/***********************/
	private native void destroyEnvironment(long env);

	/****************/
	/* commandLoop: */
	/****************/
	private native void commandLoop(long env);

	/****************/
	/* commandLoop: */
	/****************/
	public void commandLoop() {
		commandLoop(theEnvironment);
	}

	/*******************/
	/* getInputBuffer: */
	/*******************/
	private native String getInputBuffer(long env);

	/*******************/
	/* getInputBuffer: */
	/*******************/
	public String getInputBuffer() {
		return getInputBuffer(theEnvironment);
	}

	/*******************/
	/* setInputBuffer: */
	/*******************/
	private native void setInputBuffer(long env, String theString);

	/*******************/
	/* setInputBuffer: */
	/*******************/
	public void setInputBuffer(String theString) {
		setInputBuffer(theEnvironment, theString);
	}

	/************************/
	/* getInputBufferCount: */
	/************************/
	private native long getInputBufferCount(long env);

	/************************/
	/* getInputBufferCount: */
	/************************/
	public long getInputBufferCount() {
		return getInputBufferCount(theEnvironment);
	}

	/************************/
	/* setInputBufferCount: */
	/************************/
	private native long setInputBufferCount(long env, long theValue);

	/************************/
	/* setInputBufferCount: */
	/************************/
	public long setInputBufferCount(long theValue) {
		return setInputBufferCount(theEnvironment, theValue);
	}

	/**********************/
	/* expandInputBuffer: */
	/**********************/
	private native void expandInputBuffer(long env, char theChar);

	/**********************/
	/* expandInputBuffer: */
	/**********************/
	public void expandInputBuffer(char theChar) {
		expandInputBuffer(theEnvironment, theChar);
	}

	/**********************/
	/* appendInputBuffer: */
	/**********************/
	private native void appendInputBuffer(long env, String theString);

	/**********************/
	/* appendInputBuffer: */
	/**********************/
	public void appendInputBuffer(String theString) {
		appendInputBuffer(theEnvironment, theString);
	}

	/*******************************/
	/* inputBufferContainsCommand: */
	/*******************************/
	private native boolean inputBufferContainsCommand(long env);

	/*******************************/
	/* inputBufferContainsCommand: */
	/*******************************/
	public boolean inputBufferContainsCommand() {
		return inputBufferContainsCommand(theEnvironment);
	}

	/*****************************/
	/* commandLoopOnceThenBatch: */
	/*****************************/
	private native void commandLoopOnceThenBatch(long env);

	/*****************************/
	/* commandLoopOnceThenBatch: */
	/*****************************/
	public void commandLoopOnceThenBatch() {
		commandLoopOnceThenBatch(theEnvironment);
	}

	/****************/
	/* printBanner: */
	/****************/
	private native void printBanner(long env);

	/****************/
	/* printBanner: */
	/****************/
	public void printBanner() {
		printBanner(theEnvironment);
	}

	/****************/
	/* printPrompt: */
	/****************/
	private native void printPrompt(long env);

	/****************/
	/* printPrompt: */
	/****************/
	public void printPrompt() {
		printPrompt(theEnvironment);
	}

	/**************/
	/* addRouter: */
	/**************/
	private native boolean addRouter(long env, String routerName, int priority,
			Router theRouter);

	/**************/
	/* addRouter: */
	/**************/
	public boolean addRouter(Router theRouter) {
		return addRouter(theEnvironment, theRouter.getName(),
				theRouter.getPriority(), theRouter);
	}

	/***********************/
	/* incrementFactCount: */
	/***********************/
	private native void incrementFactCount(Environment javaEnv, long env,
			long fact);

	/***********************/
	/* decrementFactCount: */
	/***********************/
	private native void decrementFactCount(Environment javaEnv, long env,
			long fact);

	/***********************/
	/* incrementFactCount: */
	/***********************/
	public void incrementFactCount(FactAddressValue theFact) {
		incrementFactCount(theFact.getEnvironment(), theFact.getEnvironment()
				.getEnvironmentAddress(), theFact.getFactAddress());
	}

	/***********************/
	/* decrementFactCount: */
	/***********************/
	public void decrementFactCount(FactAddressValue theFact) {
		decrementFactCount(theFact.getEnvironment(), theFact.getEnvironment()
				.getEnvironmentAddress(), theFact.getFactAddress());
	}

	/***************************/
	/* incrementInstanceCount: */
	/***************************/
	private native void incrementInstanceCount(Environment javaEnv, long env,
			long instance);

	/***************************/
	/* decrementInstanceCount: */
	/***************************/
	private native void decrementInstanceCount(Environment javaEnv, long env,
			long instance);

	/***************************/
	/* incrementInstanceCount: */
	/***************************/
	public void incrementInstanceCount(InstanceAddressValue theInstance) {
		incrementInstanceCount(theInstance.getEnvironment(), theInstance
				.getEnvironment().getEnvironmentAddress(),
				theInstance.getInstanceAddress());
	}

	/***************************/
	/* decrementInstanceCount: */
	/***************************/
	public void decrementInstanceCount(InstanceAddressValue theInstance) {
		decrementInstanceCount(theInstance.getEnvironment(), theInstance
				.getEnvironment().getEnvironmentAddress(),
				theInstance.getInstanceAddress());
	}

	/**
	 * This will deallocate all memory associated with that environment.
	 */
	public void destroy() {
		destroyEnvironment(theEnvironment);
	}

	/*********/
	/* main: */
	/*********/
	public static void main(String args[]) {
		Environment clips;

		clips = new Environment();

		clips.commandLoop();
	}
}
