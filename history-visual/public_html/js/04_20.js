var case0420 = {

    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.json("http://localhost:8080/history/ruler/json",function(error,data) {case0420.dataViz(data)});
    },

    dataViz : function(data) {

        xScale = d3.scale.linear().domain([0,30]).range([50,1500]);
        yScale = d3.scale.linear().domain([1000,1700]).range([500,50]);

        xAxis = d3.svg.axis()
        .scale(xScale)
        .orient("bottom")
        .tickSize(450)
        //.tickValues([1,2,3,4,5,6,7,8,9,10]);

        d3.select("svg").append("g").attr("id", "xAxisG").call(xAxis);
        d3.selectAll("#xAxisG").attr("transform","translate(0,50)");

        yAxis = d3.svg.axis()
        .scale(yScale)
        .orient("right")
        .ticks(10)
        .tickSize(1450)
        .tickSubdivide(true);

        d3.select("svg").append("g").attr("id", "yAxisG").call(yAxis);
        d3.selectAll("#yAxisG").attr("transform","translate(50,0)");

        d3.select("svg").selectAll("circle.tweets")
        .data(data)
        .enter()
        .append("circle")
        .attr("class", "tweets")
        .attr("r", 5)
        .attr("cx", function(d, i) {return xScale(i)})
        .attr("cy", function(d) {return yScale(d.startYear)})
        .style("fill", "black")


        d3.select("svg").selectAll("circle.retweets")
        .data(data)
        .enter()
        .append("circle")
        .attr("class", "retweets")
        .attr("r", 5)
        .attr("cx", function(d, i) {return xScale(i)})
        .attr("cy", function(d) {return yScale(d.endYear)})
        .style("fill", "lightgray")
        /**
        d3.select("svg").selectAll("circle.favorites")
        .data(data)
        .enter()
        .append("circle")
        .attr("class", "favorites")
        .attr("r", 5)
        .attr("cx", function(d) {return xScale(d.day)})
        .attr("cy", function(d) {return yScale(d.favorites)})
        .style("fill", "gray");
        */

        tweetLine = d3.svg.line()
                .x(function(d, i) {
                return xScale(i)
            })
                .y(function(d) {
                return yScale(d.startYear)
            })

        retweetLine = d3.svg.line()
            .x(function(d, i) {
            return xScale(i)
        })
            .y(function(d) {
            return yScale(d.endYear)
        })
    /*
        favLine = d3.svg.line()
            .x(function(d) {
            return xScale(d.day)
        })
            .y(function(d) {
            return yScale(d.favorites)
        })
    */
        d3.select("svg")
            .append("path")
            .attr("d", tweetLine(data))
            .attr("fill", "none")
            .attr("stroke", "darkred")
            .attr("stroke-width", 2)


            d3.select("svg")
            .append("path")
            .attr("d", retweetLine(data))
            .attr("fill", "none")
            .attr("stroke", "gray")
            .attr("stroke-width", 3)
    /*
            d3.select("svg")
            .append("path")
            .attr("d", favLine(data))
            .attr("fill", "none")
            .attr("stroke", "black")
            .attr("stroke-width", 2)
        */
       
        d3.selectAll("circle").on("mouseover", showDetails);
       
        d3.text("modal_04_20.html", function(data) {d3.select("#details").append("div").attr("id", "modal").html(data)});
        
        function showDetails(d) {
            d3.selectAll("td.data").data(d3.values(d)).html(function(p) {return p})
        }
    }
}