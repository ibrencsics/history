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