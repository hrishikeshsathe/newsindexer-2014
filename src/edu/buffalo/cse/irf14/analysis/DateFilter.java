/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.utility.AnalysisUtility;

/**
 * @author Hrishikesh
 *
 */
public class DateFilter extends TokenFilter {

	TokenStream tempStream = null;
	public DateFilter(TokenStream stream) {
		super(stream);
		tempStream = new TokenStream();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#increment()
	 */
	@Override
	public boolean increment() throws TokenizerException {
		// TODO Auto-generated method stub
		String temp = null;
		String day = null;
		String month = null;
		String year = null;
		String time = null;
		Token token = new Token();
		String temporary = null;
		if(ts.hasNext()){
			token = ts.next();
			temp = ts.getCurrent().getTermText();
			try{
				if(temp.matches(AnalysisUtility.DAYPATTERN) && ts.hasNext()){
					if(ts.next().getTermText().matches(AnalysisUtility.DATEPATTERN+"\\,*")){
						day = (Integer.valueOf(temp) < 10 ? "0" : "") + temp;
						month = ts.getCurrent().getTermText().substring(0, 3);
						month = AnalysisUtility.monthNumber.get(month.toLowerCase());
						if(ts.hasNext())
						{
							if(ts.next().getTermText().matches(AnalysisUtility.YEARPATTERN)){
								year = ts.getCurrent().getTermText();
								if(year.contains(",")){
									year = year.replaceAll("\\,","");
									token.setTermText(year+month+day+",");
								}
								else
									token.setTermText(year+month+day);
								tempStream.set(token);
							}
							else{
								year = AnalysisUtility.DEFAULTYEAR;
								token.setTermText(year+month+day);
								tempStream.set(token);
							}
						}
					}
				}
				else if(temp.matches(AnalysisUtility.DATEPATTERN) && ts.hasNext()){
					temporary = ts.next().getTermText();
					if(temporary.matches(AnalysisUtility.DAYPATTERN+"\\,*")){
						month = temp.substring(0, 3);
						month = AnalysisUtility.monthNumber.get(month.toLowerCase());
						day = ts.getCurrent().getTermText();
						if(day.contains(","))
							day = day.replaceAll("\\,", "");
						day = (Integer.valueOf(day) < 10 ? "0" : "") + day;
						if(ts.hasNext())
						{
							if(ts.next().getTermText().matches(AnalysisUtility.YEARPATTERN)){
								year = ts.getCurrent().getTermText();
								if(year.contains(",")){
									year = year.replaceAll("\\,","");
									token.setTermText(year+month+day+",");
								}
								else
									token.setTermText(year+month+day);
								tempStream.set(token);
							}
							else{
								year = AnalysisUtility.DEFAULTYEAR;
								token.setTermText(year+month+day);
								tempStream.set(token);
								tempStream.set(ts.getCurrent());
							}
						}
						else{
							year = AnalysisUtility.DEFAULTYEAR;
							token.setTermText(year+month+day);
							tempStream.set(token);
						}
					}
					else if(temporary.matches(AnalysisUtility.YEARPATTERN)){
						month = temp.substring(0, 3);
						month = AnalysisUtility.monthNumber.get(month.toLowerCase());
						year = ts.getCurrent().getTermText();
						if(year.contains(",")){
							year = year.replaceAll("\\,","");
							token.setTermText(year+month+day+",");
						}
						else
							token.setTermText(year+month+AnalysisUtility.DEFAULTDAY);
						tempStream.set(token);
					}
					else
					{
						token.setTermText(temp);
						tempStream.set(token);
					}
				}
				else if(temp.matches("[0-9]+") && ts.hasNext()){
					if(ts.next().getTermText().matches("(AD)|(BC)")){
						String zeros = null;
						if(ts.getCurrent().getTermText().matches("AD")){
							zeros = "0000";
						}
						else if(ts.getCurrent().getTermText().matches("BC")){
							zeros = "-0000";
						}
						year = zeros.substring(0, zeros.length()-temp.length())+temp;
						token.setTermText(year+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY);
						tempStream.set(token);
					}
					else if(temp.matches(AnalysisUtility.YEARPATTERN)){
						if((Integer.valueOf(temp)>=1800) && (Integer.valueOf(temp)<=2100)){
						year = temp;
						token.setTermText(year+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY);
						tempStream.set(token);
						tempStream.set(ts.getCurrent());
						}
						else
						{
							token.setTermText(ts.getCurrent().getTermText());
							tempStream.set(token);
						}
						
					}
					else
					{
						token.setTermText(ts.getCurrent().getTermText());
						tempStream.set(token);
					}
				}
				else if(temp.matches("[0-9]+[AD|BC]+\\.*")){
					if(temp.contains("AD")){
						String zeros = "0000";
						temp = temp.replaceAll("AD", "");
						if(temp.contains(".")){
							temp=temp.replaceAll("\\.", "");
							year = zeros.substring(0, zeros.length()-temp.length())+temp+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY+".";
						}
						else
							year = zeros.substring(0, zeros.length()-temp.length())+temp+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY;
						token.setTermText(year);
						tempStream.set(token);
					}
				}
				else if(temp.matches(AnalysisUtility.TIMEPATTERN)){
					if(temp.contains("PM")){
						String zeros = ":00:00";
						int x = Integer.valueOf(temp.substring(0, temp.indexOf(":")))+12;
						temp = temp.replaceAll("PM", "");
						if(temp.contains(".")){
							time = String.valueOf(x)+temp.substring(temp.indexOf(":"), temp.length()-1)+zeros.substring(0, (zeros.length()-temp.length()+2))+".";
						}
						else
							time = String.valueOf(x)+temp.substring(temp.indexOf(":"), temp.length())+zeros.substring(0, (zeros.length()-temp.length()+2));
						token.setTermText(time);
						tempStream.set(token);
					}
					else if(ts.next().getTermText().matches("[am|AM|pm|PM]+\\.*")){
						String zeros = ":00:00";
						if(ts.getCurrent().getTermText().matches("[pm|PM]+")){
							int x = Integer.valueOf(temp.substring(0, 2))+12;
							System.out.println(x);
						}
						String y = ts.getCurrent().getTermText();
						time = temp+zeros.substring(0, (zeros.length()-temp.length()+2));
						if(y.contains(".")){
							time+=".";
						}
						token.setTermText(time);
						tempStream.set(token);
					}
				}
				else if(temp.matches(AnalysisUtility.TIMEPATTERN+"[UTC|GMT]+")){
					temp = temp.replaceAll("[UTC|GMT]+", "");
					time = temp;
					token.setTermText(time);
					tempStream.set(token);
				}
				else if(temp.matches("\\d{4}\\-\\d{2}\\.*")){
					String[] y = temp.split("-");
					if(y[1].contains(".")){
						y[1] = y[1].replaceAll("\\.", "");
						year = y[0]+"0101"+"-"+y[0].substring(0,2)+y[1]+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY+".";
					}
					else{
						year = y[0]+"0101"+"-"+y[0].substring(0,2)+y[1]+AnalysisUtility.DEFAULTMONTH+AnalysisUtility.DEFAULTDAY;
					}
					token.setTermText(year);
					tempStream.set(token);
				}
				else
					tempStream.set(token);
				
			}
			catch(Exception e){
			}
			return true;
		}
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.irf14.analysis.Analyzer#getStream()
	 */
	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return tempStream;
	}

}
