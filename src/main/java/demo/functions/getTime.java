package demo.functions;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

public class getTime extends AbstractFunction {
	// Define the function name,the name must be start with__.
	private static final String KEY = "__getTime";



	// Define the description about the parameter,this text will help user
	// understand it.
	private static final List<String> desc = new LinkedList<String>();
	static {
		desc.add("Formt the time(optional)");
	}

	// The object array use for store the user input parameter.
	private Object[] values;

	// Get the description and show in jmeter helper.
	public List<String> getArgumentDesc() {

		return desc;
	}

	
	//Logic,The result will return jmeter.
	@Override
	public String execute(SampleResult previousResult, Sampler currentSampler) throws InvalidVariableException {
		
		Date currentDate=new Date();
		String currentdate=currentDate.toString();
		//If the parameter is empty will default value.
		if(values.length>0) {
			SimpleDateFormat df = new SimpleDateFormat(((CompoundVariable) values[0]).execute().trim());
			currentdate=df.format(currentDate);
		}
	
		return currentdate;
	}

	//Covent the parameters to array.
	@Override
	public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
		
		values = parameters.toArray();// save parameter.

	}

	// Get define function name and display in jmeter helper.
	@Override
	public String getReferenceKey() {

		return KEY;
	}

}
