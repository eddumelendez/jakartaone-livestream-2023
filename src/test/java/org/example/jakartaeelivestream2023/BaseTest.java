package org.example.jakartaeelivestream2023;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class BaseTest {

    protected static byte[] war(String appserver) {
        File[] deps = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();

        WebArchive archive = ShrinkWrap.create(WebArchive.class,"jakarta-tc.war")
                .addClass(DemoApplication.class)
                .addPackages(true,
                        "org.example.jakartaeelivestream2023.anime")
                .addAsResource(appserver + "/persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(deps);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        archive.as(ZipExporter.class).exportTo(outputStream);
        return outputStream.toByteArray();
    }

}
