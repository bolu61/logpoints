package anana5.sense.logpoints;

import java.util.HashMap;
import java.util.StringJoiner;

import anana5.graph.Vertex;
import anana5.util.LList;
import anana5.util.Path;
import soot.jimple.Stmt;

public class PrintPaths {
    public static void main(String[] args) {
        Factory.v().configure(args);

        var graph = Factory.v().graph();

        graph.traverse(($, $$) -> {}).join();

        var seen = new HashMap<Vertex<Stmt>, LList<Path<Vertex<Stmt>>>>();
        LList<Path<Vertex<Stmt>>> paths = graph.fold(droplets -> droplets.flatmap(droplet -> {
            var vertex = droplet.get();

            if (seen.containsKey(vertex)) {
                return seen.get(vertex);
            }

            var paths$ = droplet.next();
            return LList.bind(paths$.isEmpty().map(isEmpty -> {
                if (isEmpty) {
                    var path$ = LList.cons(Path.<Vertex<Stmt>>nil(), LList.nil());
                    seen.put(vertex, path$);
                    return path$;
                } else {
                    var path$ = paths$.map(path -> path.push(vertex));
                    seen.put(vertex, path$);
                    return path$;
                }
            }));
        }));

        paths.traverse(path -> {
            if (path.empty()) {
                return;
            }

            var sj = new StringJoiner("\t");
            path.traverse(vertex -> {
                sj.add(String.format("nx%08x %s", vertex.id(), StmtVertexFormatter.format(vertex)));
            });
            System.out.println(sj.toString());
        }).join();
    }
    
}