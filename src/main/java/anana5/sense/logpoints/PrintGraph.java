package anana5.sense.logpoints;

import anana5.graph.rainfall.Rain;
import anana5.util.Promise;
import net.sourceforge.argparse4j.inf.Namespace;
import soot.jimple.Stmt;

public class PrintGraph {

    public static void main(String[] args) {
        Namespace ns = Cmd.parse(args);
        Factory.v().configure(ns);
        Rain<Box<Stmt>.Ref> graph = Factory.v().graph();

        // traverse graph
        try (var printer = new DotPrinter(ns.get("output"), StmtVertexFormatter::format)) {
            graph.traverse((src, tgt) -> {
                printer.print(src, tgt);
                return Promise.nil();
            }).join();
        }
    }
}
