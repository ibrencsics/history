var wiki = {
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        wiki.showControls();
        
        wiki.query("William_the_Conqueror");
    },
    
    showControls : function() {
        d3.select("#controls").append("input").attr("id", "wikiPage").attr("type", "text").style("width", "300px");
        d3.select("#controls").append("button").attr("id", "go").on("click", wiki.buttonClick).html("Go");
    },
    
    buttonClick : function() {
        wikiPage = document.getElementById("wikiPage").value;
        console.log(wikiPage);
        
        d3.select("svg").selectAll("*").remove();
        wiki.query(wikiPage);
    },
    
    query : function(wikiPage) {
        queue()
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/nodes")
            .defer(d3.csv, "http://localhost:8080/history/wiki/person/" + wikiPage + "/edges")
            .await(function(error, file1, file2) {
                wiki.dataViz(file1, file2);
            });
    },
    
    dataViz : function(nodes, edges) {
        var nodeHash = {};
        for (x in nodes) {
            nodeHash[nodes[x].id] = nodes[x];
        }
        for (x in edges) {
            if (edges[x].type == "IS_SPOUSE_OF") {
                edges[x].weight = 5;
            } else {
                edges[x].weight = 1;
            }
            edges[x].source = nodeHash[edges[x].source];
            edges[x].target = nodeHash[edges[x].target];
        }
        
        var weightScale = d3.scale.linear().domain(d3.extent(edges, function(d) {return d.weight})).range([.1,1])
        
        force = d3.layout.force()
//          .charge(-1000)
            .charge(function (d) {return d.weight * -500})
            .gravity(.3)
            .linkDistance(50)
            .linkStrength(function (d) {return weightScale(d.weight)})
            .size([500,500]).nodes(nodes)
            .links(edges).on("tick", forceTick);

        d3.select("svg").selectAll("line.link").data(edges, function (d) {return d.source.id + "-" + d.target.id}).enter()
            .append("line")
            .attr("class", "link")
            .style("stroke", "black")
            .style("opacity", .5)
            .style("stroke-width", function(d) {return d.weight});
      
        var nodeEnter = d3.select("svg").selectAll("g.node").data(nodes, function (d) {return d.id}).enter()
            .append("g")
            .attr("class", "node")
            .call(force.drag())
            .on("click", fixNode)
            .on("dblclick", releaseNode);
      
        function fixNode(d) {
//            Cif (d.fixed) {
//                d3.select(this).select("circle").style("stroke-width", 1);
//                d.fixed = false;
//            } else {
                d3.select(this).select("circle").style("stroke-width", 3);
                d.fixed = true;
//            }
        }
        
        function releaseNode(d) {
            d3.select(this).select("circle").style("stroke-width", 1);
            d.fixed = false;
        }
      
        nodeEnter.append("circle")
            .attr("r", 8)
            .style("fill", "BLUE")
            .style("stroke", "black")
            .style("stroke-width", "1px");

        nodeEnter.append("text")
            .style("text-anchor", "middle")
            .attr("y", 15)
            .text(function(d) {return d.name})

//      d3.selectAll("line").attr("marker-end", "url(#Triangle)");
        
        force.start();

        function forceTick() {
            d3.selectAll("line.link")
                .attr("x1", function (d) {return d.source.x})
                .attr("x2", function (d) {return d.target.x})
                .attr("y1", function (d) {return d.source.y})
                .attr("y2", function (d) {return d.target.y});

            d3.selectAll("g.node")
                .attr("transform", function (d) {return "translate("+d.x+","+d.y+")"})
        }
    }
}