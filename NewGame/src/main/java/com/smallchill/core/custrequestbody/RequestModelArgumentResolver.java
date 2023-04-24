//package com.smallchill.core.custrequestbody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.smallchill.core.annotation.RequestPropertiesBody;
//import org.apache.shiro.util.ClassUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//
///**
// * @author 半卷流年
// * @date 2020-8-13 10:28
// */
//public class RequestModelArgumentResolver implements HandlerMethodArgumentResolver {
//
//    private final Pattern underLinePattern = Pattern.compile("_(\\w)");
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//       return parameter.hasParameterAnnotation(RequestPropertiesBody.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        Class<?> parameterType = parameter.getParameterType();
//
//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
//        String body = requestWrapper.getBody();
//        //实例化对象
//        Object result = ClassUtils.newInstance(parameterType);
//        if(StringUtils.isEmpty(body)){
//            return result;
//        }
//        JSONObject jsonObject = JSONObject.parseObject(body);
//        //获取字段
//        Field[] fields = FieldUtil.getFields(parameterType);
//        Map<String, Field> fieldMap = Arrays.stream(fields).collect(Collectors.toMap(Field::getName, v -> v));
//        Object arg = null;
//        //往对象写入值
//        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
//            String fieldName = entry.getKey();
//            //处理下划线转驼峰命名
//            if(isExistUnderLine(fieldName)){
//                fieldName = this.underLineToCamel(fieldName);
//            }
//            Field field = fieldMap.getOrDefault(fieldName, null);
//            if(ObjectUtils.isEmpty(field)){
//                continue;
//            }
//            arg = entry.getValue();
//            if(ObjectUtils.isEmpty(arg)){
//                continue;
//            }
//            if (isPrimitive(field.getType())) {
//                Class<?> parType = field.getType();
//                arg = getArg(parType, arg);
//            }
//            Method setter = getSetterMethod(parameterType, field,fieldName);
//            if (setter != null) {
//                setter.invoke(result, arg);
//            }
//        }
//        return result;
//    }
//
//
//    private boolean isExistUnderLine(String value){
//        Matcher m = this.underLinePattern.matcher(value);
//       return m.find();
//    }
//
//
//    private String underLineToCamel(final String value) {
//        final StringBuffer sb = new StringBuffer();
//        Matcher m = this.underLinePattern.matcher(value);
//        while (m.find()){
//            m.appendReplacement(sb, m.group(1).toUpperCase());
//        }
//        m.appendTail(sb);
//        return sb.toString();
//    }
//
//
//    private Method getSetterMethod(Class<?> clazz, Field field,String fileName) throws NoSuchMethodException {
//        return clazz.getDeclaredMethod("set" + toUpperCaseFirstOne(fileName), field.getType());
//    }
//
//    private boolean isPrimitive(Class<?> clazz) {
//        return Integer.class.equals(clazz) ||
//                Double.class.equals(clazz) ||
//                Float.class.equals(clazz) ||
//                Boolean.class.equals(clazz) ||
//                Long.class.equals(clazz) ||
//                Byte.class.equals(clazz) ||
//                Short.class.equals(clazz) ||
//                clazz.isPrimitive();
//
//    }
//
//
//    private Object getArg(Class<?> primitiveClass, Object value) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Object result = null;
//        if (isPrimitive(primitiveClass)) {
//            String primitiveName = primitiveClass.getSimpleName();
//            if ("char".equals(primitiveName)) {
//                return value;
//            }
//            String firstUpperprimitiveName = toUpperCaseFirstOne(primitiveName);
//            Class<?> encapsulationClass = "int".equals(primitiveName) ? Class.forName("java.lang." + firstUpperprimitiveName + "eger") : Class.forName("java.lang." + firstUpperprimitiveName);
//            result = getParseMethod(encapsulationClass, firstUpperprimitiveName).invoke(null, value);
//        }
//        return result;
//    }
//
//    private Method getParseMethod(Class<?> encapsulationClass, String firstUpperprimitiveName) throws NoSuchMethodException {
//        String methodName = "parse" + firstUpperprimitiveName;
//        return encapsulationClass.getDeclaredMethod(methodName, String.class);
//    }
//
//    private String toUpperCaseFirstOne(String fieldName) {
//        if (Character.isUpperCase(fieldName.charAt(0))){
//            return fieldName;
//        }
//        else {
//            return String.valueOf(Character.toUpperCase(fieldName.charAt(0))) + fieldName.substring(1);
//        }
//    }
//
//}