var case0410 = {

    show : function() {
        d3.select("svg").selectAll("*").remove();
        d3.select("#details").selectAll("*").remove();
        d3.json("http://localhost:8080/history/person/json",function(error,data) {case0410.dataViz(data)});
    },

    dataViz : function(incomingData) {

        xScale = d3.scale.linear().domain([1000,1700]).range([50,1500]);
        yScale = d3.scale.linear().domain([0,100]).range([500,50]);

        xAxis = d3.svg.axis().scale(xScale).orient("bottom").tickSize(450).ticks(10);
        d3.select("svg").append("g").attr("id", "xAxisG").call(xAxis);
        d3.selectAll("#xAxisG").attr("transform","translate(0,50)");

        yAxis = d3.svg.axis().scale(yScale).orient("left").tickSize(1450).ticks(4);
        d3.select("svg").append("g").attr("id", "yAxisG").call(yAxis);
        d3.selectAll("#yAxisG").attr("transform","translate(1500,0)");

        d3.select("svg").selectAll("circle").data(incomingData).enter().append("circle").attr("r", 5)
        .attr("cx", function(d) {return xScale(d.yearOfBirth)}).attr("cy", function(d) {return yScale(d.yearOfDeath-d.yearOfBirth)})


        d3.selectAll("circle").on("mouseover", showDetails);

//        d3.text("modal.html", function(data) {d3.select("body").append("div").attr("id", "modal").html(data)});
        d3.text("modal_04_10.html", function(data) {d3.select("#details").append("div").attr("id", "modal").html(data)});
        
        function showDetails(d) {
            d3.selectAll("td.data").data(d3.values(d)).html(function(p) {return p})
        }
    }
}
