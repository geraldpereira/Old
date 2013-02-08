package fr.byob.blog.server.dozer;

import java.util.ArrayList;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

public class Mapper {

    private final static ArrayList<String> configFiles = new ArrayList<String>();
    static {
        configFiles.add("fr/byob/blog/server/dozer/dozer-mapping.xml");
    }
    private final static MapperIF mapper = new DozerBeanMapper(configFiles);
    public static MapperIF getMapper (){
        return mapper;
    }
}
