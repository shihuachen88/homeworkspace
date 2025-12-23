useEffect(() => {
  fetch("/api/ecfr/wordcount")
    .then(res => res.json())
    .then(setData);
}, []);

return (
  <table>
    <thead>
      <tr>
        <th>Agency</th>
        <th>Word Count</th>
      </tr>
    </thead>
    <tbody>
      {data.map(row => (
        <tr key={row.agency}>
          <td>{row.agency}</td>
          <td>{row.wordCount.toLocaleString()}</td>
        </tr>
      ))}
    </tbody>
  </table>
);