package org.eclipse.jst.j2ee.web;

import java.util.*;
import org.eclipse.jst.j2ee.internal.common.operations.*;
import org.eclipse.jst.j2ee.internal.web.operations.*;

public class ServletTemplate
{
  protected static String nl;
  public static synchronized ServletTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ServletTemplate result = new ServletTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";";
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "import ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "/**" + NL + " * Servlet implementation class ";
  protected final String TEXT_9 = NL + " *" + NL + " * @web.servlet" + NL + " *   name=\"";
  protected final String TEXT_10 = "\"" + NL + " *   display-name=\"";
  protected final String TEXT_11 = "\"";
  protected final String TEXT_12 = NL + " *   description=\"";
  protected final String TEXT_13 = "\"";
  protected final String TEXT_14 = NL + " *" + NL + " * @web.servlet-mapping" + NL + " *   url-pattern=\"";
  protected final String TEXT_15 = "\"";
  protected final String TEXT_16 = NL + " *" + NL + " * @web.servlet-init-param" + NL + " *    name=\"";
  protected final String TEXT_17 = "\"" + NL + " *    value=\"";
  protected final String TEXT_18 = "\"";
  protected final String TEXT_19 = NL + " *    description=\"";
  protected final String TEXT_20 = "\"";
  protected final String TEXT_21 = NL + " */";
  protected final String TEXT_22 = NL + "public ";
  protected final String TEXT_23 = "abstract ";
  protected final String TEXT_24 = "final ";
  protected final String TEXT_25 = "class ";
  protected final String TEXT_26 = " extends ";
  protected final String TEXT_27 = " implements ";
  protected final String TEXT_28 = ", ";
  protected final String TEXT_29 = " {";
  protected final String TEXT_30 = NL + "\tprivate static final long serialVersionUID = 1L;";
  protected final String TEXT_31 = NL + NL + "    /**" + NL + "     * Default constructor. " + NL + "     */" + NL + "    public ";
  protected final String TEXT_32 = "() {" + NL + "        // TODO Auto-generated constructor stub" + NL + "    }";
  protected final String TEXT_33 = NL + "       " + NL + "    /**" + NL + "     * @see ";
  protected final String TEXT_34 = "#";
  protected final String TEXT_35 = "(";
  protected final String TEXT_36 = ")" + NL + "     */" + NL + "    public ";
  protected final String TEXT_37 = "(";
  protected final String TEXT_38 = ") {" + NL + "        super(";
  protected final String TEXT_39 = ");" + NL + "        // TODO Auto-generated constructor stub" + NL + "    }";
  protected final String TEXT_40 = NL + NL + "\t/**" + NL + "     * @see ";
  protected final String TEXT_41 = "#";
  protected final String TEXT_42 = "(";
  protected final String TEXT_43 = ")" + NL + "     */" + NL + "    public ";
  protected final String TEXT_44 = " ";
  protected final String TEXT_45 = "(";
  protected final String TEXT_46 = ") {" + NL + "        // TODO Auto-generated method stub";
  protected final String TEXT_47 = NL + "\t\t\treturn ";
  protected final String TEXT_48 = ";";
  protected final String TEXT_49 = NL + "    }";
  protected final String TEXT_50 = NL + NL + "\t/**" + NL + "\t * @see Servlet#init(ServletConfig)" + NL + "\t */" + NL + "\tpublic void init(ServletConfig config) throws ServletException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_51 = NL + NL + "\t/**" + NL + "\t * @see Servlet#destroy()" + NL + "\t */" + NL + "\tpublic void destroy() {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_52 = NL + NL + "\t/**" + NL + "\t * @see Servlet#getServletConfig()" + NL + "\t */" + NL + "\tpublic ServletConfig getServletConfig() {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t\treturn null;" + NL + "\t}";
  protected final String TEXT_53 = NL + NL + "\t/**" + NL + "\t * @see Servlet#getServletInfo()" + NL + "\t */" + NL + "\tpublic String getServletInfo() {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t\treturn null; " + NL + "\t}";
  protected final String TEXT_54 = NL + NL + "\t/**" + NL + "\t * @see Servlet#service(ServletRequest request, ServletResponse response)" + NL + "\t */" + NL + "\tpublic void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_55 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)" + NL + "\t */" + NL + "\tprotected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_56 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)" + NL + "\t */" + NL + "\tprotected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_57 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)" + NL + "\t */" + NL + "\tprotected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_58 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)" + NL + "\t */" + NL + "\tprotected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_59 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)" + NL + "\t */" + NL + "\tprotected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_60 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)" + NL + "\t */" + NL + "\tprotected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_61 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)" + NL + "\t */" + NL + "\tprotected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_62 = NL + NL + "\t/**" + NL + "\t * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)" + NL + "\t */" + NL + "\tprotected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {" + NL + "\t\t// TODO Auto-generated method stub" + NL + "\t}";
  protected final String TEXT_63 = NL + NL + "}";
  protected final String TEXT_64 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     CreateServletTemplateModel model = (CreateServletTemplateModel) argument; 
    
	model.removeFlags(CreateJavaEEArtifactTemplateModel.FLAG_QUALIFIED_SUPERCLASS_NAME); 

    
	if (model.getJavaPackageName() != null && model.getJavaPackageName().length() > 0) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append( model.getJavaPackageName() );
    stringBuffer.append(TEXT_2);
    
	}

    stringBuffer.append(TEXT_3);
     
	Collection<String> imports = model.getImports();
	for (String anImport : imports) { 

    stringBuffer.append(TEXT_4);
    stringBuffer.append( anImport );
    stringBuffer.append(TEXT_5);
     
	}

    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append( model.getClassName() );
     
	if (model.isAnnotated()) { 

    stringBuffer.append(TEXT_9);
    stringBuffer.append( model.getServletName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( model.getServletName() );
    stringBuffer.append(TEXT_11);
     
		if (model.getDescription() != null && model.getDescription().length() > 0) { 

    stringBuffer.append(TEXT_12);
    stringBuffer.append( model.getDescription() );
    stringBuffer.append(TEXT_13);
     
		} 
		
		List<String[]> mappings = model.getServletMappings();
 		if (mappings != null && mappings.size() > 0) {
			for (int i = 0; i < mappings.size(); i++) {
				String map = model.getServletMapping(i); 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( map );
    stringBuffer.append(TEXT_15);
     
			} 
		}
 		List<String[]> initParams = model.getInitParams();
 		if (initParams != null && initParams.size() > 0) {
    		for (int i = 0; i < initParams.size(); i++) {
				String name = model.getInitParam(i, CreateServletTemplateModel.NAME);
				String value = model.getInitParam(i, CreateServletTemplateModel.VALUE);
 				String description = model.getInitParam(i, CreateServletTemplateModel.DESCRIPTION); 

    stringBuffer.append(TEXT_16);
    stringBuffer.append( name );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( value );
    stringBuffer.append(TEXT_18);
     
				if (description != null && description.length() > 0) { 

    stringBuffer.append(TEXT_19);
    stringBuffer.append( description );
    stringBuffer.append(TEXT_20);
    
				}
			} 
		} 
	} 

    stringBuffer.append(TEXT_21);
    
	if (model.isPublic()) { 

    stringBuffer.append(TEXT_22);
     
	} 

	if (model.isAbstract()) { 

    stringBuffer.append(TEXT_23);
    
	}

	if (model.isFinal()) {

    stringBuffer.append(TEXT_24);
    
	}

    stringBuffer.append(TEXT_25);
    stringBuffer.append( model.getClassName() );
    
	String superClass = model.getSuperclassName();
 	if (superClass != null && superClass.length() > 0) {

    stringBuffer.append(TEXT_26);
    stringBuffer.append( superClass );
    
	}

	List<String> interfaces = model.getInterfaces(); 
 	if ( interfaces.size() > 0) { 

    stringBuffer.append(TEXT_27);
    
	}
	
 	for (int i = 0; i < interfaces.size(); i++) {
   		String INTERFACE = (String) interfaces.get(i);
   		if (i > 0) {

    stringBuffer.append(TEXT_28);
    
		}

    stringBuffer.append( INTERFACE );
    
	}

    stringBuffer.append(TEXT_29);
     
	if (model.isGenericServletSuperclass()) { 

    stringBuffer.append(TEXT_30);
     
	} 

     
	if (!model.hasEmptySuperclassConstructor()) { 

    stringBuffer.append(TEXT_31);
    stringBuffer.append( model.getClassName() );
    stringBuffer.append(TEXT_32);
     
	} 

	if (model.shouldGenSuperclassConstructors()) {
		List<Constructor> constructors = model.getConstructors();
		for (Constructor constructor : constructors) {
			if (constructor.isPublic() || constructor.isProtected()) { 

    stringBuffer.append(TEXT_33);
    stringBuffer.append( model.getSuperclassName() );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( model.getSuperclassName() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( constructor.getParamsForJavadoc() );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( model.getClassName() );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( constructor.getParamsForDeclaration() );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( constructor.getParamsForCall() );
    stringBuffer.append(TEXT_39);
    
			} 
		} 
	} 

    
	if (model.shouldImplementAbstractMethods()) {
		for (Method method : model.getUnimplementedMethods()) { 

    stringBuffer.append(TEXT_40);
    stringBuffer.append( method.getContainingJavaClass() );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( method.getName() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( method.getParamsForJavadoc() );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( method.getReturnType() );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( method.getName() );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( method.getParamsForDeclaration() );
    stringBuffer.append(TEXT_46);
     
			String defaultReturnValue = method.getDefaultReturnValue();
			if (defaultReturnValue != null) { 

    stringBuffer.append(TEXT_47);
    stringBuffer.append( defaultReturnValue );
    stringBuffer.append(TEXT_48);
    
			} 

    stringBuffer.append(TEXT_49);
     
		}
	} 

     if (model.shouldGenInit()) { 
    stringBuffer.append(TEXT_50);
     } 
     if (model.shouldGenDestroy()) { 
    stringBuffer.append(TEXT_51);
     } 
     if (model.shouldGenGetServletConfig()) { 
    stringBuffer.append(TEXT_52);
     } 
     if (model.shouldGenGetServletInfo()) { 
    stringBuffer.append(TEXT_53);
     } 
     if (model.shouldGenService() && !model.isHttpServletSuperclass()) { 
    stringBuffer.append(TEXT_54);
     } 
     if (model.shouldGenService() && model.isHttpServletSuperclass()) { 
    stringBuffer.append(TEXT_55);
     } 
     if (model.shouldGenDoGet()) { 
    stringBuffer.append(TEXT_56);
     } 
     if (model.shouldGenDoPost()) { 
    stringBuffer.append(TEXT_57);
     } 
     if (model.shouldGenDoPut()) { 
    stringBuffer.append(TEXT_58);
     } 
     if (model.shouldGenDoDelete()) { 
    stringBuffer.append(TEXT_59);
     } 
     if (model.shouldGenDoHead()) { 
    stringBuffer.append(TEXT_60);
     } 
     if (model.shouldGenDoOptions()) { 
    stringBuffer.append(TEXT_61);
     } 
     if (model.shouldGenDoTrace()) { 
    stringBuffer.append(TEXT_62);
     } 
    stringBuffer.append(TEXT_63);
    stringBuffer.append(TEXT_64);
    return stringBuffer.toString();
  }
}
