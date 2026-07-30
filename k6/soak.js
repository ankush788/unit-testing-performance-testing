import http from "k6/http";
import { check } from "k6";

export const options = {
    vus: 100,
    duration: "5m",
};

export default function () {
    const response = http.get("http://localhost:8080/users");

    check(response, {
        "Status is 200": (r) => r.status === 200,
    });
}