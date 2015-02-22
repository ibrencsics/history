var englishKingsCh3 = {
    
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.csv("data/english-kings.csv", function(data) {
            englishKingsCh3.dataViz(data);
        })
    },
    
    dataViz : function(data) {
        
        d3.select("svg")
            .append("g")
            .attr("id", "teamsG")
            .attr("transform", "translate(50,100)")
            .selectAll("g")
            .data(data)
            .enter()
            .append("g")
            .attr("class", "overallG")
            .attr("transform",
            function (d,i) {return "translate(" + ((i%10) * 100) + ", " + (Math.floor(i/10) * 70) + ")"}
            );

        var teamG = d3.selectAll("g.overallG");
        
//        teamG
//            .append("circle")
//            .attr("r", 20)
//            .style("fill", "pink")
//            .style("stroke", "black")
//            .style("stroke-width", "1px");
        teamG
            .append("circle").attr("r", 0)
            .transition()
            .delay(function(d,i) {return i * 50})
            .duration(500)
            .attr("r", 40)
            .transition()
            .duration(500)
            .attr("r", 20);
        teamG
            .append("text")
            .style("text-anchor", "middle")
            .attr("y", 30)
            .style("font-size", "10px")
            .text(function(d) {return d.name;});
    
        // mouseover
    
        teamG.on("mouseover", highlightRegion);
        function highlightRegion(d) {
            d3.selectAll("g.overallG").select("circle").style("fill", function(p) {
                return p.name == d.name ? "red" : "gray";
            });
        };
        
        teamG.on("mouseout", function() {
            d3.selectAll("g.overallG").select("circle").style("fill", "pink");
        });
        
        // mouseclick
    
        d3.text("modal-english-kings-ch3.html", function(data) {
            d3.select("#details").append("div").attr("id", "modal").html(data);
        });

        teamG.on("click", teamClick);

        function teamClick(d) {
            d3.selectAll("td.data").data(d3.values(d))
            .html(function(p) {
                return p
            });
        };
        
        // control
    
        var dataKeys = d3.keys(data[0]).filter(function(el) {
            return el == "spouses" || el == "issues";
        });
        
        d3.select("#controls")
                .selectAll("button.auto")
                .data(dataKeys)
                .enter()
                .append("button")
                .on("click", buttonClick)
                .html(function(d) {return d;});
        
        function buttonClick(datapoint) {
            var maxValue = d3.max(data, function(d) {
                return parseFloat(d[datapoint]);
            });
            var radiusScale = d3.scale.linear()
                .domain([ 0, maxValue ]).range([ 2, 20 ]);

            d3.selectAll("g.overallG").select("circle")
                    .transition().duration(1000)
                    .attr("r", function(d) {
                return radiusScale(d[datapoint]);
            });
        }
        
        d3.select("#controls")
                .append("button")
                .on("click", reignClick)
                .html("reign");
        
        function reignClick() {
            var maxValue = d3.max(data, function(d) {
                return parseFloat(d.reignEnd) - parseFloat(d.reignStart);
            });
            
            var radiusScale = d3.scale.linear().domain([0,maxValue]).range([2,20]);
            
            d3.selectAll("g.overallG").select("circle")
                    .transition().duration(1000)
                    .attr("r", function(d) {
                return radiusScale(parseFloat(d.reignEnd) - parseFloat(d.reignStart));
            });
        }
    }  
}

var englishKingsCh4 = {
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.csv("data/english-kings.csv", function(data) {
            englishKingsCh4.dataViz(data);
        })
    },
    
    dataViz : function(incomingData) {
        
        var data = incomingData
                .filter(function(el) { return el.birth > 0 })
                .sort(function(a,b) { return parseFloat(a.birth) - parseFloat(b.birth) });
        
        x_min = 50;
        x_max = 700;
        y_min = 50;
        y_max = 500;
        
        offset = 750;
        
        var xScale = d3.scale.linear().domain([1000,2000]).range([x_min,x_max]);
        var yScale = d3.scale.linear().domain([0,100]).range([y_max,y_min]);
        
        xAxis = d3.svg.axis().scale(xScale).orient("bottom").tickSize(y_max-y_min).ticks(10);
        d3.select("svg").append("g").attr("id", "xAxisG").attr("transform","translate(0," + y_min + ")").call(xAxis);

        yAxis = d3.svg.axis().scale(yScale).orient("left").ticks(10).tickSize(x_max-x_min).tickSubdivide(10);
        d3.select("svg").append("g").attr("id", "yAxisG").attr("transform","translate(" + x_max +",0)").call(yAxis);

        d3.select("svg").selectAll("circle.life").data(data)
                .enter()
                .append("circle")
                .attr("class", "life")
                .attr("r", 5)
                .attr("cx", function(d) {return xScale(d.birth)})
                .attr("cy", function(d) {return yScale(d.death - d.birth)})
                .style("fill", "black");        
        
        // -----------------
        
        var yExtent2 = d3.extent(data, function(d) { return d.issues } );
        var xScale2 = d3.scale.linear().domain([1000,2000]).range([x_min,x_max]);
        var yScale2 = d3.scale.linear().domain([0,17]).range([y_max,y_min]);
        
        xAxis2 = d3.svg.axis().scale(xScale2).orient("bottom").tickSize(y_max-y_min).ticks(10);
        d3.select("svg").append("g").attr("id", "xAxisG2").attr("transform","translate(" + offset + "," + y_min + ")").call(xAxis2);

        yAxis2 = d3.svg.axis().scale(yScale2).orient("left").ticks(10).tickSize(x_max-x_min).tickSubdivide(10);
        d3.select("svg").append("g").attr("id", "yAxisG2").attr("transform","translate(" + (x_max+offset) +",0)").call(yAxis2);
        
        d3.select("svg").selectAll("circle.spouse").data(data)
                .enter()
                .append("circle")
                .attr("class", "spouse")
                .attr("r", 5)
                .attr("cx", function(d) {return offset+xScale2(d.birth)})
                .attr("cy", function(d) {return yScale2(d.spouses)})
                .style("fill", "blue");        
  
//        d3.select("body").selectAll("p").data(data).enter().append("p").text(function(d) {return d.name + " : " + d.birth + " : " + d.spouses});
 
        d3.select("svg").selectAll("circle.issue").data(data)
                .enter()
                .append("circle")
                .attr("class", "issue")
                .attr("r", 5)
                .attr("cx", function(d) {return offset+xScale2(d.birth)})
                .attr("cy", function(d) {return yScale2(d.issues)})
                .style("fill", "red");        
        
        var spousesLine = d3.svg.line()
            .x(function(d) {
                return offset+xScale2(d.birth);
            })
            .y(function(d) {
            return yScale2(d.spouses);
            });
            
        spousesLine.interpolate("cardinal");
            
        d3.select("svg")
            .append("path")
            .attr("d", spousesLine(data))
            .attr("fill", "none")
            .attr("stroke", "blue")
            .attr("stroke-width", 2);
    
        var issuesLine = d3.svg.line()
            .x(function(d) {
                return offset+xScale2(d.birth);
            })
            .y(function(d) {
            return yScale2(d.issues);
            });
            
//        issuesLine.interpolate("cardinal");
            
        d3.select("svg")
            .append("path")
            .attr("d", issuesLine(data))
            .attr("fill", "none")
            .attr("stroke", "red")
            .attr("stroke-width", 2);
    }
}

var englishKingsCh5 = {
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        d3.csv("data/english-kings.csv", function(data) {
            englishKingsCh5.dataViz(data);
        })
    },
    
    dataViz : function(incomingData) {
        
        var data = incomingData
                .filter(function(el) { return el.birth > 0 && el.issues > 9 })
                .sort(function(a,b) { return parseFloat(a.birth) - parseFloat(b.birth) });
        
        var colorScale = d3.scale.category10([0,1,2,3,4,5,6,7,8,9]);

        pieChart = d3.layout.pie().sort(null);
        pieChart.value(function(d) {return d.issues});
        newArc = d3.svg.arc();
        newArc.outerRadius(100).innerRadius(20);
        
        d3.select("svg")
            .append("g")
            .attr("transform","translate(250,250)")
            .selectAll("path")
            .data(pieChart(data))
            .enter()
            .append("path")
            .attr("d", newArc)
            .style("fill", function(d, i) {return colorScale(i)})
            .style("opacity", .5)
            .style("stroke", "black")
            .style("stroke-width", "2px")
            .each(function(d) { this._current = d; });
    
        
        pieChart.value(function(d) {return d.spouses});
        d3.selectAll("path").data(pieChart(data))
            .transition().duration(1000).attr("d", newArc);
    
//        function arcTween(a) {
//            var i = d3.interpolate(this._current, a);
//            this._current = i(0);
//            return function(t) {
//                return newArc(i(t));
//            };
//        }
    }
}

var englishKingsCh6 = {
    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.select("#controls").selectAll("*").remove();
        
        queue()
            .defer(d3.csv, "data/nodes.csv")
            .defer(d3.csv, "data/edges.csv")
//            .defer(d3.csv, "data/book/nodelist.csv")
//            .defer(d3.csv, "data/book/edgelist.csv")
            .await(function(error, file1, file2) {
                englishKingsCh6.dataViz(file1, file2);
            });
    },
    
    dataViz : function(nodes,edges) {
//        var marker = d3.select("svg").append('defs')
//            .append('marker')
//            .attr("id", "Triangle")
//            .attr("refX", 12)
//            .attr("refY", 6)
//            .attr("markerUnits", 'userSpaceOnUse')
//            .attr("markerWidth", 12)
//            .attr("markerHeight", 18)
//            .attr("orient", 'auto')
//            .append('path')
//            .attr("d", 'M 0 0 12 6 0 12 3 6');
            
        var nodeHash = {};
        for (x in nodes) {
            nodeHash[nodes[x].id] = nodes[x];
        }
        for (x in edges) {
            edges[x].weight = parseInt(edges[x].weight);
            edges[x].source = nodeHash[edges[x].source];
            edges[x].target = nodeHash[edges[x].target];
        }
        
//      chargeScale = d3.scale.linear().domain(d3.extent(nodes, function(d) {return d.followers})).range([-500,-2000])
//      nodeSize = d3.scale.linear().domain(d3.extent(nodes, function(d) {return d.followers})).range([5,20])
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
            .style("fill", function(d) { return d.gender=='m' ? "BLUE" : "RED" } )
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
